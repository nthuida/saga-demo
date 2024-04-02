package com.huida.learn.saga.composite;

import com.huida.learn.saga.enums.StatusEnum;
import com.huida.learn.saga.http.RequestMsg;
import com.huida.learn.saga.http.ResponseBody;
import com.huida.learn.saga.journal.model.OutBoundJournal;
import com.huida.learn.saga.journal.service.OutboundJournalService;
import com.huida.learn.saga.util.ControllerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 组合交易入口
 * @author: huida
 * @date: 2024/3/19
 **/
@Slf4j
@Component
public class CompositeService {

    @Autowired
    private OutboundJournalService outboundJournalService;

    @Autowired
    private ServiceModelConfigParser serviceModelConfigParser;

    public Object execute(RequestMsg request){

        ResponseBody responseBody = new ResponseBody();
        responseBody.setSysEvtTraceId(request.getSysEvtTraceId());
        responseBody.setTxTypeInd(request.getTxTypeInd());
        responseBody.setSysTxCode(request.getSysTxCode());
        responseBody.setSysTxStatus(StatusEnum.SUCCESS.getCode());
        responseBody.setRvrsStcd(StatusEnum.UNDO.getCode());
        responseBody.setSysRespCode("00000000");
        responseBody.setSysRespDesc("交易成功");

        String serviceName = request.getSysTxCode();
        List<Step> steps = serviceModelConfigParser.getModelByServiceName(serviceName);
        if (steps == null) {
            log.error("Service '" + serviceName + "' not found.");
            responseBody.setSysTxStatus(StatusEnum.FAIL.getCode());
            responseBody.setSysRespCode("00000001");
            responseBody.setSysRespDesc("交易配置未找到");
            return responseBody;
        }

        for (int i=0; i<steps.size(); i++) {
            log.info("模型执行第{}次", i+1);
            String service = steps.get(i).getService();
            Map<String, String> args = steps.get(i).getArgs();
            String compensate = steps.get(i).getCompensate();
            String condition = steps.get(i).getCondition();

            if (condition != null && !condition.isEmpty() && !evaluateCondition(condition)) {
                log.info("Condition not met for service: " + service);
                continue;
            }
            OutBoundJournal outBoundJournal = new OutBoundJournal();
            outBoundJournal.setSysEvtTraceId(request.getSysEvtTraceId());
            outBoundJournal.setTxTypeInd(request.getTxTypeInd());
            outBoundJournal.setStepSn(i+1);
            outBoundJournal.setSysTxCode(service);
            ControllerContext.getContext().setOutCallMsg(outBoundJournal);
            outboundJournalService.normalBeforeProcess();
            // 在此处调用相应的原子服务
            log.info("Calling service: " + service + " with args: " + args);
            //模拟外呼的结果
            boolean flag = new Random().nextBoolean();
            if (flag) {
                outBoundJournal.setSysTxStatus(StatusEnum.SUCCESS.getCode());
                outBoundJournal.setSysRespCode("00000000");
                outBoundJournal.setSysRespDesc("交易成功");
            } else {
                outBoundJournal.setSysTxStatus(StatusEnum.FAIL.getCode());
                outBoundJournal.setSysRespCode("00000001");
                outBoundJournal.setSysRespDesc("交易失败");
            }
            ControllerContext.getContext().setOutCallMsg(outBoundJournal);
            outboundJournalService.normalAfterProcess();
            if (!flag) {
                responseBody.setSysTxStatus(StatusEnum.FAIL.getCode());
                responseBody.setSysRespCode("00000001");
                responseBody.setSysRespDesc("交易失败");
                break;
            }
        }

        return responseBody;
    }

    public boolean evaluateCondition(String condition) {
        // 在实际应用中，根据具体情况实现条件的评估
        // 这里简单地假设条件都满足
        return true;
    }


}
