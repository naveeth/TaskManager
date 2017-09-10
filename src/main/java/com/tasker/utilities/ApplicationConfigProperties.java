package com.tasker.utilities;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by njanibasha on 9/7/17.
 */
@Component
public class ApplicationConfigProperties {

    @Value("${resources.producer.threadCorePoolSize}")
    private String coreThreadPoolSize;

    @Value("${resources.producer.threadMaxPoolSize}")
    private String maxThreadPoolSize;

    @Value("${resources.consumer.threadCorePoolSize}")
    private String coreConsumerThreadPoolSize;

    @Value("${resources.consumer.threadMaxPoolSize}")
    private String maxConsumerThreadPoolSize;

    public int getCoreConsumerThreadPoolSize() {
        if (StringUtils.isBlank(coreConsumerThreadPoolSize)) {
            return 2;
        }

        return Integer.parseInt(coreThreadPoolSize);
    }

    public void setCoreConsumerThreadPoolSize(String coreConsumerThreadPoolSize) {
        this.coreConsumerThreadPoolSize = coreConsumerThreadPoolSize;
    }

    public int getMaxConsumerThreadPoolSize() {
        if (StringUtils.isBlank(maxConsumerThreadPoolSize)) {
            return 4;
        }

        return Integer.parseInt(maxConsumerThreadPoolSize);
    }

    public void setMaxConsumerThreadPoolSize(String maxConsumerThreadPoolSize) {
        this.maxConsumerThreadPoolSize = maxConsumerThreadPoolSize;
    }

    public int getCoreThreadPoolSize() {
        if (StringUtils.isBlank(coreThreadPoolSize)) {
            return 2;
        }

        return Integer.parseInt(coreThreadPoolSize);
    }

    public void setCoreThreadPoolSize(String coreThreadPoolSize) {
        this.coreThreadPoolSize = coreThreadPoolSize;
    }

    public int getMaxThreadPoolSize() {
        if (StringUtils.isBlank(maxThreadPoolSize)) {
            return 4;
        }

        return Integer.parseInt(maxThreadPoolSize);
    }

    public void setMaxThreadPoolSize(String maxThreadPoolSize) {
        this.maxThreadPoolSize = maxThreadPoolSize;
    }

}
