package com.huida.learn.saga.controller;

import com.huida.learn.saga.http.RequestMsg;
import com.huida.learn.saga.http.ResponseMsg;
import com.huida.learn.saga.journal.handler.Handler;
import com.huida.learn.saga.util.ControllerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * saga服务入口
 * @author: huida
 * @date: 2024/3/27
 **/

@RestController
@RequestMapping("/saga")
public class DispatchController {

    @Autowired
    private Handler chainHandler;

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ResponseMsg start(HttpServletRequest request, @RequestBody RequestMsg requestMsg) {

        chainHandler.handle(requestMsg);
        return new ResponseMsg<>(ControllerContext.getContext().getOutput());
    }

}
