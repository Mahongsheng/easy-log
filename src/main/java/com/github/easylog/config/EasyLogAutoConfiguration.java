package com.github.easylog.config;

import com.github.easylog.recorder.DefaultLogRecorder;
import com.github.easylog.recorder.LogRecorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


/**
 * 定义配置类
 *
 * @author Hansel Ma
 * @since 2022/3/31
 */
@ComponentScan("com.github.easylog")
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
public class EasyLogAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(EasyLogAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(value = LogRecorder.class)
    public LogRecorder nothingCollector() {
        return new DefaultLogRecorder();
    }

    @Bean
    @ConditionalOnMissingBean(name = "threadPoolForRecorder")
    public Executor threadPoolForRecorder() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(256);
        executor.setThreadNamePrefix("recorder-exec-");
        executor.setRejectedExecutionHandler((r, exec) -> log.error("threadPoolForRecorder thread queue is full,activeCount:{},Subsequent collection tasks will be rejected", exec.getActiveCount()));
        executor.initialize();
        return executor;
    }
}
