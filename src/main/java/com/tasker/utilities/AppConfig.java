package com.tasker.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by njanibasha on 9/7/17.
 */
@Configuration
public class AppConfig {

    @Autowired
    ApplicationConfigProperties appConfiguration;

    @Bean(name = "producerTaskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(appConfiguration.getCoreThreadPoolSize());
        pool.setMaxPoolSize(appConfiguration.getMaxThreadPoolSize());
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean(name = "consumerTaskExecutor")
    public ThreadPoolTaskExecutor consumerTaskExecutor() {
        ThreadPoolTaskExecutor consumerPool = new ThreadPoolTaskExecutor();
        consumerPool.setCorePoolSize(appConfiguration.getCoreConsumerThreadPoolSize());
        consumerPool.setMaxPoolSize(appConfiguration.getMaxConsumerThreadPoolSize());
        consumerPool.setWaitForTasksToCompleteOnShutdown(true);
        return consumerPool;
    }

}
