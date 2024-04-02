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

import java.util.Random;
import java.util.UUID;

/**
 * 组合交易入口
 * @author: huida
 * @date: 2024/3/19
 **/
@Slf4j
@Component
public class IntegCompositeService {

    @Autowired
    private OutboundJournalService outboundJournalService;

    public Object execute(RequestMsg request){
        // 模拟模型的执行
        ResponseBody responseBody = new ResponseBody();
        responseBody.setSysEvtTraceId(request.getSysEvtTraceId());
        responseBody.setTxTypeInd(request.getTxTypeInd());
        responseBody.setSysTxCode(request.getSysTxCode());
        responseBody.setSysTxStatus(StatusEnum.SUCCESS.getCode());
        responseBody.setRvrsStcd(StatusEnum.UNDO.getCode());
        responseBody.setSysRespCode("00000000");
        responseBody.setSysRespDesc("交易成功");

        for (int i=0; i<3; i++) {
            log.info("模拟模型执行第{}次", i+1);
            OutBoundJournal outBoundJournal = new OutBoundJournal();
            outBoundJournal.setSysEvtTraceId(request.getSysEvtTraceId());
            outBoundJournal.setTxTypeInd(request.getTxTypeInd());
            outBoundJournal.setStepSn(i+1);
            //模拟外呼的交易码
            outBoundJournal.setSysTxCode(getUUID().substring(0, 9));
            ControllerContext.getContext().setOutCallMsg(outBoundJournal);
            outboundJournalService.normalBeforeProcess();
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

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
