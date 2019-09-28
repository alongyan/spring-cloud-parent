package com.george.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 菜单资源表 前端控制器
 * </p>
 *
 * @author George Chan
 * @since 2019-09-28
 */
@Controller
@CrossOrigin
@RequestMapping("/api/resources")
public class ResourcesController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
}

