package com.info.maeumgagym.global.config.scheduler

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
@EnableScheduling
class SchedulerConfig {

    @Bean
    fun scheduler(): TaskScheduler =
        ThreadPoolTaskScheduler().apply {
            poolSize = 2 // 동시에 실행될 수 있는 작업 수
        }
}
