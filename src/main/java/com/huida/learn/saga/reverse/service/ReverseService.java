package com.huida.learn.saga.reverse.service;

import com.huida.learn.saga.journal.model.InBoundJournal;
import com.huida.learn.saga.reverse.model.ReverseResult;

/**
 * @author: huida
 * @date: 2024/3/28
 **/
public interface ReverseService {

    /**
     * 外部冲正
     * @param journal
     */
    ReverseResult doOutReverse(InBoundJournal journal);

    /**
     * 内部冲正
     * @param journal
     */
    ReverseResult doInnerReverse(InBoundJournal journal);
}
