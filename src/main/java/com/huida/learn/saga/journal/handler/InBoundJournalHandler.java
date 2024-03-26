package com.huida.learn.saga.journal.handler;

/**
 * @author: huida
 * @date: 2024/3/18
 **/
public class InBoundJournalHandler {

    public void beforeProcess() {
        System.out.println("InBoundJournalHandler beforeProcess");
    }

    public void afterProcess() {
        System.out.println("InBoundJournalHandler afterProcess");
    }
}
