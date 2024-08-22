package com.info.maeumgagym.infrastructure.config.interceptor

import com.info.maeumgagym.infrastructure.processor.annotation.WebAdapterMethodAnnotationResolver
import com.info.maeumgagym.infrastructure.response.locationheader.LocationHeaderInterceptor
import com.info.maeumgagym.presentation.common.locationheader.LocationHeaderManager
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class InterceptorChainConfig(
    private val locationHeaderManager: LocationHeaderManager,
    private val webAdapterMethodAnnotationResolver: WebAdapterMethodAnnotationResolver
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(
            LocationHeaderInterceptor(
                locationHeaderManager
            )
        ).addPathPatterns("/**")

        registry.addInterceptor(
            webAdapterMethodAnnotationResolver
        ).addPathPatterns("/**")
    }
}
