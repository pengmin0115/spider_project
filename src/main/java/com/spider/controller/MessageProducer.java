package com.spider.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class MessageProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.producer.topic}")
    private String topic;

    public void sendMessage(UpdateStatusMessage message) {

        ReplyMsg resp = rocketMQTemplate.sendAndReceive(topic, message, ReplyMsg.class, 20 * 1000);

        log.info("producer received response: {}", JSON.toJSONString(resp));

        /*rocketMQTemplate.asyncSend(topic, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("send message success");
            }

            @Override
            public void onException(Throwable e) {
                log.info("send message error: ", e);
            }
        });*/
    }

}
