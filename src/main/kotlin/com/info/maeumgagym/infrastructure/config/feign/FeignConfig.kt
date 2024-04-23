package com.info.maeumgagym.config.feign

import com.info.maeumgagym.error.feign.FeignClientErrorDecoder
import feign.Logger
import feign.codec.ErrorDecoder
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@EnableFeignClients(basePackages = ["com.info.maeumgagym"])
@Configuration
@Import(FeignClientErrorDecoder::class)
class FeignConfig {

    @Bean
    fun feignLoggerLevel(): Logger.Level = Logger.Level.FULL

    @Bean
    @ConditionalOnMissingBean(value = [ErrorDecoder::class]) //Bean 오버라이딩시 충돌 해결
    fun commonFeignErrorDecoder(): FeignClientErrorDecoder = FeignClientErrorDecoder()
}
