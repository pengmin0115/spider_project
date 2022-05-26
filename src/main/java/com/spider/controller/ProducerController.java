package com.spider.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author pengmin
 * @date 2022/5/27 01:31
 */
@RestController
@RequestMapping("/producer")
@Slf4j
public class ProducerController {

    @Resource
    private MessageProducer messageProducer;

    @RequestMapping("/req")
    public String sendMessage() {
        try {
            messageProducer.sendMessage(UpdateStatusMessage.builder()
                    .srcId(String.valueOf(System.currentTimeMillis()))
                    .status("cancel")
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "ok";
    }
}
