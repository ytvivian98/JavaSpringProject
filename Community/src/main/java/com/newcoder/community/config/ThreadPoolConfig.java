package com.newcoder.community.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yt
 * date 2022-07-22
 * 定时线程执行 的 相关配置
 */
@Configuration
@EnableScheduling
@EnableAsync
public class ThreadPoolConfig {
}
