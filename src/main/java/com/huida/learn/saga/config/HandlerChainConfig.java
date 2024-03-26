package com.huida.learn.saga.config;

import com.huida.learn.saga.journal.handler.Handler;
import com.huida.learn.saga.builder.ChainBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;

/**
 * @author: huida
 * @date: 2024/3/18
 **/
@Configuration
public class HandlerChainConfig {

    @Value("${chain.xml.path}")
    private String chainXmlPath;

    @Bean
    public Handler chainHandler() {
        return new ChainBuilder().buildChainFromXML(chainXmlPath);
    }

    @Bean
    public ApplicationRunner initializeChain() {
        return args -> {
            // 调用 chainHandler() 方法触发责任链的构建
            chainHandler();
        };
    }
}
