package com.huida.learn.saga.journal.service;

import com.huida.learn.saga.journal.model.OutBoundJournal;

/**
 * @author: huida
 * @date: 2024/3/28
 **/
public interface OutboundJournalService {

    OutBoundJournal getOutBoundJournal(String sysEvtTraceId, String txTypeInd, String sysTxCode);

    void beforeProcess();

    void afterProcess();


}
