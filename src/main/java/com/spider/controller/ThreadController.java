package com.spider.controller;

import com.alibaba.fastjson.JSON;
import com.spider.dao.UserDao;
import com.spider.jsoup.UserInfo;
import com.spider.utils.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengmin
 * @date 2021/5/12 20:59
 */
@RestController
@RequestMapping("/thread")
public class ThreadController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/getUserInfo", method = {RequestMethod.POST,RequestMethod.GET})
    public Object getUserInfo(){
        Map resMap = new HashMap();
        UserInfo userByName = userDao.getUserByName("vito ditaranto");
        resMap.put("singleUser",userByName);
        ThreadUtils thread = new ThreadUtils();
        thread.run();
        while (!thread.isFlag()) {

        }
        List<UserInfo> list = thread.getResults();
        resMap.put("sum",list.size());
        resMap.put("list",list);
        return JSON.parse(JSON.toJSONString(resMap));
    }

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


