package com.huida.learn.saga.reverse.handler;

import com.huida.learn.saga.enums.StatusEnum;
import com.huida.learn.saga.enums.TypeEnum;
import com.huida.learn.saga.http.RequestMsg;
import com.huida.learn.saga.http.ResponseBody;
import com.huida.learn.saga.journal.model.InBoundJournal;
import com.huida.learn.saga.journal.service.InboundJournalService;
import com.huida.learn.saga.journal.service.impl.InBoundJournalHandler;
import com.huida.learn.saga.reverse.model.ReverseResult;
import com.huida.learn.saga.reverse.service.ReverseService;
import com.huida.learn.saga.util.ControllerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * 外部冲正处理器
 * @author: huida
 * @date: 2024/3/19
 **/
@Slf4j
@Component("synReverseHandler")
public class SynReverseHandler {

    @Autowired
    private ReverseService reverseService;

    @Autowired
    private InBoundJournalHandler inBoundJournalHandler;

    @Autowired
    private InboundJournalService inboundJournalService;

    public void handle(Object input) {
        RequestMsg requestMsg = (RequestMsg) input;
        ControllerContext.getContext().setInput(input);
        InBoundJournal normalJournal = inboundJournalService.getJournalByPrimaryKey(requestMsg.getSysEvtTraceId(), TypeEnum.NORMAL.getCode(), requestMsg.getSysTxCode());
        if (normalJournal == null) {
            //冲正比原交易先到，正交易时序异常，冲正交易成功
            InBoundJournal journal = buildInBoundJournal(requestMsg);
            journal.setSysTxStatus(StatusEnum.SEQUENCE_ERROR.getCode());
            journal.setRvrsStcd(StatusEnum.FAIL.getCode());
            inboundJournalService.insert(journal);

            journal.setTxTypeInd(TypeEnum.REVERSE.getCode());
            journal.setSysTxStatus(StatusEnum.SUCCESS.getCode());
            journal.setSysRespTime(new Date());
            inboundJournalService.insert(journal);

            ResponseBody responseBody = buildResponseBody(requestMsg);
            responseBody.setSysTxStatus(StatusEnum.SUCCESS.getCode());
            ControllerContext.getContext().setOutput(responseBody);

            return;
        } else {
            if (normalJournal.getRvrsStcd().equals(StatusEnum.DOING.getCode())) {
                //执行中
                ResponseBody responseBody = buildResponseBody(requestMsg);
                responseBody.setSysTxStatus(StatusEnum.UNKNOWN.getCode());
                ControllerContext.getContext().setOutput(responseBody);
                return;
            } else if (normalJournal.getRvrsStcd().equals(StatusEnum.SUCCESS.getCode()) && normalJournal.getSysTxStatus().equals(StatusEnum.FAIL.getCode())) {
                //原交易失败,冲正成功
                ResponseBody responseBody = buildResponseBody(requestMsg);
                responseBody.setSysTxStatus(StatusEnum.SUCCESS.getCode());
                ControllerContext.getContext().setOutput(responseBody);
                return;
            } else if (normalJournal.getRvrsStcd().equals(StatusEnum.UNDO.getCode()) && normalJournal.getSysTxStatus().equals(StatusEnum.SUCCESS.getCode())) {
                //原交易成功,冲正未执行，符合条件
                ResponseBody responseBody = buildResponseBody(requestMsg);
                inBoundJournalHandler.reverseBeforeProcess();
                ReverseResult result = null;
                try {
                    result = reverseService.doOutReverse(normalJournal);
                    if (!ObjectUtils.isEmpty(result)) {
                        if (StatusEnum.SUCCESS.getCode().equals(result.getStatus())) {
                            responseBody.setSysTxStatus(StatusEnum.SUCCESS.getCode());

                        } else {
                            //冲正失败
                            responseBody.setSysTxStatus(StatusEnum.FAIL.getCode());
                        }
                    }
                } catch (Exception e) {
                    //冲正异常
                    responseBody.setSysTxStatus(StatusEnum.UNKNOWN.getCode());
                    log.error("synReverseHandler doOutReverse error", e);
                } finally {
                    ControllerContext.getContext().setOutput(responseBody);
                    inBoundJournalHandler.reverseAfterProcess(result);
                }

                return;
            } else {
                //直接返回冲正结果
                ResponseBody responseBody = buildResponseBody(requestMsg);
                responseBody.setSysTxStatus(normalJournal.getRvrsStcd());
                ControllerContext.getContext().setOutput(responseBody);
                return;
            }
        }


    }


    private InBoundJournal buildInBoundJournal(RequestMsg requestMsg) {
        InBoundJournal inBoundJournal = new InBoundJournal();
        inBoundJournal.setSysEvtTraceId(requestMsg.getSysEvtTraceId());
        inBoundJournal.setSysTxCode(requestMsg.getSysTxCode());
        inBoundJournal.setTxTypeInd(TypeEnum.NORMAL.getCode());
        inBoundJournal.setRcrdRgtm(new Date());
        inBoundJournal.setSysReqTime(new Date());

        return inBoundJournal;
    }

    private ResponseBody buildResponseBody(RequestMsg requestMsg) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setSysEvtTraceId(requestMsg.getSysEvtTraceId());
        responseBody.setTxTypeInd(requestMsg.getTxTypeInd());
        responseBody.setSysTxCode(requestMsg.getSysTxCode());

        return responseBody;
    }


}
