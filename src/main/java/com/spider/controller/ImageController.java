package com.spider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengmin
 * @date 2022/5/5 20:53
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @RequestMapping("/test")
    public Object test() {
        return "ok";
    }
}
