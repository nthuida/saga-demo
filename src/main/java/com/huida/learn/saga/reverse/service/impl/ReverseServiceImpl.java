package com.huida.learn.saga.reverse.service.impl;

import com.huida.learn.saga.journal.model.InBoundJournal;
import com.huida.learn.saga.reverse.service.ReverseService;
import org.springframework.stereotype.Service;

/**
 * @author: huida
 * @date: 2024/3/18
 **/
@Service
public class ReverseServiceImpl implements ReverseService {

    /**
     * 外部冲正
     * @param journal
     */
    @Override
    public void doOutReverse(InBoundJournal journal){

    }

    /**
     * 内部冲正
     * @param journal
     */
    @Override
    public void doInnerReverse(InBoundJournal journal){

    }

}
