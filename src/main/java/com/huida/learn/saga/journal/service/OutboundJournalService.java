package com.huida.learn.saga.journal.service;

import com.huida.learn.saga.journal.model.OutBoundJournal;
import com.huida.learn.saga.reverse.model.ReverseResult;

import java.util.List;

/**
 * @author: huida
 * @date: 2024/3/28
 **/
public interface OutboundJournalService {

    OutBoundJournal getOutBoundJournal(String sysEvtTraceId, String txTypeInd, String sysTxCode);

    void normalBeforeProcess();

    void normalAfterProcess();

    void reverseBeforeProcess(OutBoundJournal outBoundJournal);

    void reverseAfterProcess(OutBoundJournal outBoundJournal, ReverseResult reverseResult);

    List<OutBoundJournal> getReverseList(String sysEvtTraceId, String txTypeInd);


}
