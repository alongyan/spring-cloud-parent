package com.george.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author GeorgeChan 2019/10/2 17:33
 * @version 1.0
 * @since jdk1.8
 */
@RestController
public class ConfigClientController {

    @Value("${test.env}")
    private String hello;

    @GetMapping("/name")
    public String test() {
        return this.hello;
    }
}
