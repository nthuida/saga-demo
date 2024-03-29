package com.huida.learn.saga.journal.service.impl;

import com.huida.learn.saga.journal.model.OutBoundJournal;
import com.huida.learn.saga.journal.service.OutboundJournalService;
import org.springframework.stereotype.Service;

/**
 * @author: huida
 * @date: 2024/3/18
 **/
@Service
public class OutboundJournalServiceImpl implements OutboundJournalService {


    @Override
    public OutBoundJournal getOutBoundJournal(String sysEvtTraceId, String txTypeInd, String sysTxCode) {
        return null;
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
