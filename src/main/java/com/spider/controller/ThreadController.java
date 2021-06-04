package com.spider.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author pengmin
 * @date 2021/5/12 20:59
 */
@RestController
@RequestMapping("/thread")
public class ThreadController {

    @RequestMapping(value="/test",method = RequestMethod.POST)
    public String testThread(@RequestBody Map map) {
        final String str = (String) (map.get("param"));

        new Thread(() -> {
            try {
                Thread.sleep(10000);
                System.out.println(str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return "ok";
    }

    @RequestMapping(value="/test2",method = RequestMethod.POST)
    public String sync() throws InterruptedException {
        System.out.println("进入方法体中....");
        Thread.sleep(20000);
        return "ok";
    }

}


