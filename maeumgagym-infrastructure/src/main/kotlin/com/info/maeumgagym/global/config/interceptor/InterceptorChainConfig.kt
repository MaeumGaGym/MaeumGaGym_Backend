package com.info.maeumgagym.global.config.interceptor

import com.info.maeumgagym.locationheader.LocationHeaderInterceptor
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class InterceptorChainConfig(
    private val locationHeaderInterceptor: LocationHeaderInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(locationHeaderInterceptor).addPathPatterns("/**")
    }
}
