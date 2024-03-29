package com.huida.learn.saga.journal.service.impl;

import com.huida.learn.saga.enums.StatusEnum;
import com.huida.learn.saga.http.RequestMsg;
import com.huida.learn.saga.http.ResBodyBO;
import com.huida.learn.saga.journal.model.InBoundJournal;
import com.huida.learn.saga.journal.service.InboundJournalService;
import com.huida.learn.saga.util.ControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: huida
 * @date: 2024/3/18
 **/
@Service
public class InBoundJournalHandler {

    @Autowired
    private InboundJournalService inboundJournalService;

    public void beforeProcess() {
        RequestMsg requestMsg = (RequestMsg) ControllerContext.getContext().getInput();
        InBoundJournal inBoundJournal = InBoundJournal.builder()
                .sysEvtTraceId(requestMsg.getSysEvtTraceId())
                .sysTxCode(requestMsg.getSysTxCode())
                .txTypeInd(requestMsg.getTxTypeInd())
                .sysTxStatus(StatusEnum.DOING.getCode())
                .rvrsStcd(StatusEnum.UNDO.getCode())
                .sysReqTime(new Date())
                .rcrdRgtm(new Date()).build();
        inboundJournalService.insert(inBoundJournal);

    }

    public void afterProcess() {
        ResBodyBO res = (ResBodyBO) ControllerContext.getContext().getOutput();
        InBoundJournal inBoundJournal = InBoundJournal.builder()
                .sysEvtTraceId(res.getSysEvtTraceId())
                .sysTxCode(res.getSysTxCode())
                .txTypeInd(res.getTxTypeInd())
                .sysTxStatus(res.getSysTxStatus())
                .sysRespCode(res.getSysRespCode())
                .sysRespDesc(res.getSysRespDesc())
                .rvrsStcd(res.getRvrsStcd())
                .sysRespTime(new Date()).build();
        inboundJournalService.updateByPrimaryKey(inBoundJournal);

    }
}
