package com.huida.learn.saga.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 交换区
 * @author: huida
 * @date: 2024/3/26
 **/
public class ControllerContext {

    private static ThreadLocal<ControllerContext> controllerContext = new ThreadLocal<ControllerContext>();

    private Map<Object,Object> swapMap;

    /**
     * 系统事件跟踪ID
     */
    private static final String SYS_EVT_TRACE_ID = "SYS_EVT_TRACE_ID";

    /**
     * 请求信息
     */
    private static final String INPUT_REQUEST = "input_request";

    /**
     * 响应信息
     */
    private static final String OUTPUT_RESPONSE = "output_response";


    /**
     * 外呼信息
     */
    private static final String OUT_MSG = "out_msg";


    private ControllerContext() {
        swapMap = new HashMap<Object,Object>();
    }

    private static void setContext(ControllerContext context){
        controllerContext.set(context);
    }

    public static ControllerContext getContext(){
        ControllerContext context = controllerContext.get();
        if(null == context){
            context = new ControllerContext();
            setContext(context);
        }
        return context;
    }

    public Object get(Object key) {
        return swapMap.get(key);
    }

    public void put(Object key, Object value) {
        swapMap.put(key, value);
    }


    public void setSysEvtTraceId(String SysEvtTraceId) {
        put(SYS_EVT_TRACE_ID, SysEvtTraceId);
    }

    public String getSysEvtTraceId() {
        return (String) get(SYS_EVT_TRACE_ID);
    }

    public void setInput(Object input) {
        put(INPUT_REQUEST, input);
    }

    public Object getInput() {
        return get(INPUT_REQUEST);
    }

    public void setOutput(Object output) {
        put(OUTPUT_RESPONSE, output);
    }

    public Object getOutput() {
        return get(OUTPUT_RESPONSE);
    }

    public void setOutCallMsg(Object outMsg) {
        put(OUT_MSG, outMsg);
    }

    public Object getOutCallMsg() {
        return get(OUT_MSG);
    }


    public void removeContext(){
        controllerContext.remove();
    }


}
