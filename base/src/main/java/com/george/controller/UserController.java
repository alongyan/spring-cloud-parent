package com.george.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author George Chan
 * @since 2019-09-28
 */
@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


}

