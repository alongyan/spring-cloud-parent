package com.george;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>
 *  客户端服务消费者  启动类
 * </p>
 *
 * @author GeorgeChan 2019/10/2 13:13
 * @version 1.0
 * @since jdk1.8
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
