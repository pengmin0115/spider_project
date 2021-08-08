package com.spider.mail;

import com.spider.dao.MailDao;
import com.spider.mail.entity.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * @author pengmin
 * @date 2021/8/9 0:09
 */
@RequestMapping(value = "/mail")
@RestController
public class MailController {

    @Autowired
    private MailDao mailDao;

    @RequestMapping("/upload")
    public RespResult uploadMail(@RequestBody Map map){
        try {
            String subtitle = "subtitle";
            if (map.get("subtitle") != null) {
                subtitle = (String) map.get("subtitle");
            }
            String content = (String) map.get("content");
            mailDao.insert(subtitle,content,new Date());
            return new RespResult(200,"ok");
        } catch (Exception e) {
            e.printStackTrace();
            return new RespResult(400,e.getMessage());
        }
    }

}
