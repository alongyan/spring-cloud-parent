package com.george;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * <p>
 *  eureka client 服务启动类（模拟与producer1的集群）
 * </p>
 *
 * @author GeorgeChan 2019/10/2 11:21
 * @version 1.0
 * @since jdk1.8
 */
@SpringBootApplication
@EnableEurekaClient
public class Product2Application {
    public static void main(String[] args) {
        SpringApplication.run(Product2Application.class, args);
    }
}
