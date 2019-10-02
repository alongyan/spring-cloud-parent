package com.george.controller;

import com.george.util.StringPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author GeorgeChan 2019/10/2 11:54
 * @version 1.0
 * @since jdk1.8
 */
@RestController
public class HelloController {
    private static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/test1")
    public String test1(@RequestParam String name) {
        return "Get Request ===》 hello " + name + StringPool.COMMA + "producer1 is Ready";
    }

    @GetMapping("/test2/{name}")
    public String test2(@PathVariable(value = "name", required = false) String name) {
        return "Post Request ===》 hello " + name + StringPool.COMMA + "producer1 is Ready";
    }
}
