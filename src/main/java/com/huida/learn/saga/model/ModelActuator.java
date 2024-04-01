package com.huida.learn.saga.model;

import com.huida.learn.saga.enums.StatusEnum;
import com.huida.learn.saga.http.ResBodyBO;
import com.huida.learn.saga.http.RequestMsg;
import com.huida.learn.saga.journal.model.OutBoundJournal;
import com.huida.learn.saga.journal.service.OutboundJournalService;
import com.huida.learn.saga.util.ControllerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

/**
 * 模型执行类
 * @author: huida
 * @date: 2024/3/18
 **/
@Component
@Slf4j
public class ModelActuator {

    @Autowired
    private OutboundJournalService outboundJournalService;

    public Object execute(RequestMsg request){
        // 模拟模型的执行
        ResBodyBO resBodyBO = new ResBodyBO();
        resBodyBO.setSysEvtTraceId(request.getSysEvtTraceId());
        resBodyBO.setTxTypeInd(request.getTxTypeInd());
        resBodyBO.setSysTxCode(request.getSysTxCode());
        resBodyBO.setSysTxStatus(StatusEnum.SUCCESS.getCode());
        resBodyBO.setRvrsStcd(StatusEnum.UNDO.getCode());
        resBodyBO.setSysRespCode("00000000");
        resBodyBO.setSysRespDesc("成功");

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
                resBodyBO.setSysTxStatus(StatusEnum.FAIL.getCode());
                resBodyBO.setSysRespCode("00000001");
                resBodyBO.setSysRespDesc("交易失败");
                break;
            }
        }
        return resBodyBO;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
