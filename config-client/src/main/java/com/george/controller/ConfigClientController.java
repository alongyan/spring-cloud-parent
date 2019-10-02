package com.george.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  @RefreshScope 使用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
 *                  手动刷新配置：当配置文件发生改动的时候，需要访问接口  curl -X POST http://localhost:9001/actuator/refresh
 *                  [
 *                      "config.client.version",
 *                      "test.env"
 *                  ]
 *                  表示刷新成功！
 *                  localhost:9001 客户端ip和端口
 *
 *  如果使用bus总线刷新配置，请求：curl -X POST http://127.0.0.1:9001/actuator/bus-refresh
 *  所有配置都会刷新
 * </p>
 *
 * @author GeorgeChan 2019/10/2 17:33
 * @version 1.0
 * @since jdk1.8
 */
@RefreshScope
@RestController
public class ConfigClientController {

    @Value("${test.env}")
    private String hello;

    @GetMapping("/name")
    public String test() {
        return this.hello;
    }
}
