package com.huida.learn.saga;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan({"com.huida.learn.saga.journal.mapper", "com.huida.learn.saga.reverse.mapper"})
@EnableScheduling
public class SagaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SagaApplication.class, args);
    }

}
