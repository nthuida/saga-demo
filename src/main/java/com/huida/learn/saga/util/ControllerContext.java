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


    public void removeContext(){
        controllerContext.remove();
    }


}
