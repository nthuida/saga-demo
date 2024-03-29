package com.huida.learn.saga.journal.service;

import com.huida.learn.saga.journal.model.InBoundJournal;

/**
 * @author: huida
 * @date: 2024/3/28
 **/
public interface InboundJournalService {

    InBoundJournal getInBoundJournal(String sysEvtTraceId, String txTypeInd, String sysTxCode);

    void updateByPrimaryKey(InBoundJournal inBoundJournal);

    void insert(InBoundJournal inBoundJournal);
}
