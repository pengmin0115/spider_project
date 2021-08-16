package com.spider.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: pengmin
 * @date: 2021/8/4 13:57
 */

@Configuration
@EnableAsync
public class SyncConfiguration {

    @Bean(name = "scorePoolTaskExecutor")
    public ThreadPoolTaskExecutor getScorePoolTaskExecutor () {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数;
        taskExecutor.setCorePoolSize(10);
        // 线程池维护的线程的最大数量, 只有缓冲队列的线程
        taskExecutor.setMaxPoolSize(100);
        // 缓存队列
        taskExecutor.setQueueCapacity(50);
        // 允许的空闲之间, 数量超出核心线程数的线程在空闲时间大于200s后就被销毁;
        taskExecutor.setKeepAliveSeconds(200);
        // 异步方法内部线程名称;
        taskExecutor.setThreadNamePrefix("score-");

        /**
         * 线程数达到最大数量后,还有任务到来采取的拒绝策略;
         *
         * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
         * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
         * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
         * ThreadPoolExecutor.CallerRunsPolicy：重试添加当前的任务，自动重复调用 execute() 方法，直到成功
         */
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }
}
