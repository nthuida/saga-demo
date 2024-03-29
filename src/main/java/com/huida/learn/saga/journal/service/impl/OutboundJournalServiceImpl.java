package com.huida.learn.saga.journal.service.impl;

import com.huida.learn.saga.enums.StatusEnum;
import com.huida.learn.saga.journal.mapper.OutBoundJournalMapper;
import com.huida.learn.saga.journal.model.OutBoundJournal;
import com.huida.learn.saga.journal.service.OutboundJournalService;
import com.huida.learn.saga.util.ControllerContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: huida
 * @date: 2024/3/18
 **/
@Service
public class OutboundJournalServiceImpl implements OutboundJournalService {

    @Autowired
    private OutBoundJournalMapper outboundJournalMapper;


    @Override
    public OutBoundJournal getOutBoundJournal(String sysEvtTraceId, String txTypeInd, String sysTxCode) {
        return outboundJournalMapper.selectByPrimaryKey(sysEvtTraceId, txTypeInd, sysTxCode);
    }

    @Override
    public void beforeProcess(){
        OutBoundJournal journal = (OutBoundJournal)ControllerContext.getContext().getOutCallMsg();
        journal.setRcrdRgtm(new Date());
        journal.setSysReqTime(new Date());
        journal.setRvrsStcd(StatusEnum.UNDO.getCode());
        journal.setSysTxStatus(StatusEnum.DOING.getCode());
        outboundJournalMapper.insertSelective(journal);

    }

    private void normalProcess(){


    }

    private void reverseProcess(){

    }

    @Override
    public void afterProcess(){
        OutBoundJournal journal = (OutBoundJournal)ControllerContext.getContext().getOutCallMsg();
        journal.setSysRespTime(new Date());
        outboundJournalMapper.updateByPrimaryKeySelective(journal);
    }

    private void normalAfterProcess(){

    }

    private void reverseAfterProcess(){

    }


}
