package com.spider.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: pengmin
 * @date: 2021/8/4 15:40
 */
@RestController
@RequestMapping(value = "/syncTest")
public class SyncController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTest.class);

    @Autowired
    private ServiceTest serviceTest;

    @RequestMapping("/test")
    public void test(){
        LOGGER.info("========== step into controller ==========");
        serviceTest.testAsync();
        LOGGER.info("========== finish controller ==========");
    }
}
