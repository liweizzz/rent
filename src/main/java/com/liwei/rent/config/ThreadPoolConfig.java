package com.liwei.rent.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
public class ThreadPoolConfig {

    private static final int CORE_POOL_SIZE = 2;

    private static final int MAX_POOL_SIZE = 4;

    private static final int QUEUE_CAPACITY = 10;

    private static final String THREAD_NAME_PREFIX = "SAVE-LOGIN-LOG-TASK-";

    /**
     * 异步任务
     */
    @Bean("asyncTaskExecutor")
    public ThreadPoolTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 1: 核心线程数
        threadPoolTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        // 2: 指定最大线程数,只有在缓冲队列满了之后才会申请超过核心线程数的线程
        threadPoolTaskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        // 3: 队列中最大的数目
        threadPoolTaskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        // 4: 线程名称前缀
        threadPoolTaskExecutor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        // 5: 当前线程数量已经达到MaxPoolSize的时候，如何处理新任务
        // CallerRunsPolicy: 会在execute 方法的调用线程中运行被拒绝的任务,如果执行程序已关闭，则会丢弃该任务
        // AbortPolicy: 抛出java.util.concurrent.RejectedExecutionException异常
        // DiscardOldestPolicy: 抛弃旧的任务
        // DiscardPolicy: 抛弃当前的任务
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 6: 线程空闲后的最大存活时间(默认值 60),当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        threadPoolTaskExecutor.setKeepAliveSeconds(10);
        // 7: 线程空闲时间,当线程空闲时间达到keepAliveSeconds(秒)时,线程会退出,直到线程数量等于corePoolSize,如果allowCoreThreadTimeout=true,则会直到线程数量等于0
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(false);
        threadPoolTaskExecutor.initialize();

        log.info("异步保存登录日志线程池配置成功,核心线程数:{},线程名称前缀:{}", threadPoolTaskExecutor.getCorePoolSize(), threadPoolTaskExecutor.getThreadNamePrefix());
        return threadPoolTaskExecutor;
    }

}
