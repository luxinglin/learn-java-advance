/*
 * Copyright (c) 2020 PioneerService, Inc. All Rights Reserved.
 */
package cn.com.gary.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author luxinglin
 * @version 1.0
 * @Description: TODO
 * @create 2020-02-17 21:39
 **/
@SpringBootApplication
@Slf4j
public class MqApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqApplication.class, args);
    }
}
