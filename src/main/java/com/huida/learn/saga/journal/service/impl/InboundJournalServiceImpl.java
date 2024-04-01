package com.huida.learn.saga.journal.service.impl;

import com.huida.learn.saga.journal.mapper.InBoundJournalMapper;
import com.huida.learn.saga.journal.model.InBoundJournal;
import com.huida.learn.saga.journal.service.InboundJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: huida
 * @date: 2024/3/18
 **/
@Service
public class InboundJournalServiceImpl implements InboundJournalService {

    @Autowired
    private InBoundJournalMapper inBoundJournalMapper;

    @Override
    public InBoundJournal getJournalByPrimaryKey(String sysEvtTraceId, String txTypeInd, String sysTxCode) {
        return inBoundJournalMapper.selectByPrimaryKey(sysEvtTraceId,txTypeInd,sysTxCode);
    }

    @Override
    public void updateByPrimaryKey(InBoundJournal inBoundJournal) {
        inBoundJournalMapper.updateByPrimaryKeySelective(inBoundJournal);
    }

    @Override
    public void insert(InBoundJournal inBoundJournal) {
        inBoundJournalMapper.insertSelective(inBoundJournal);
    }

    public void beforeProcess(){

    }

    private void normalProcess(){

    }

    private void reverseProcess(){

    }


    public void afterProcess(){

    }

    private void normalAfterProcess(){

    }

    private void reverseAfterProcess(){

    }
}
