package com.spider;

import com.spider.controller.UpdateStatusMessage;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author pengmin
 * @date 2021/3/28 21:40
 */
@SpringBootApplication
@MapperScan({"com.spider.dao"})
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class,args);
    }

    /*@Resource
    private RocketMQTemplate rocketMQTemplate;

    @Bean
    CommandLineRunner commandLineRunner() {
        return  args -> {
            List<UpdateStatusMessage> messages = rocketMQTemplate.receive(UpdateStatusMessage.class);
            System.out.println(messages);
        };
    }*/

}
