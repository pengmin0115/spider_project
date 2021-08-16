package com.spider.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author: pengmin
 * @date: 2021/8/4 15:38
 */

@Service
public class ServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTest.class);

    @Async(value = "scorePoolTaskExecutor")
    public void testAsync() {
        try {
            Thread.sleep(1000);
            LOGGER.info("======= 处理任务 =======");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
