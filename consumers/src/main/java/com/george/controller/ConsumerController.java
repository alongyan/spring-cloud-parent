package com.george.controller;

import com.george.remote.ProducerRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author GeorgeChan 2019/10/2 13:22
 * @version 1.0
 * @since jdk1.8
 */
@RestController
public class ConsumerController {
    private final ProducerRemote producerRemote;

    @Autowired
    public ConsumerController(ProducerRemote producerRemote) {
        this.producerRemote = producerRemote;
    }

    @GetMapping("/testCon/{name}")
    public String testConsumer(@PathVariable(value = "name")String name) {
        return producerRemote.test1(name);
    }
}
