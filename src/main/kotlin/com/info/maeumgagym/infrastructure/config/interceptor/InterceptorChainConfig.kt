package com.info.maeumgagym.infrastructure.config.interceptor

import com.info.maeumgagym.infrastructure.response.locationheader.LocationHeaderInterceptor
import com.info.maeumgagym.infrastructure.security.access.manager.AccessManager
import com.info.maeumgagym.presentation.controller.common.locationheader.LocationHeaderManager
import com.info.maeumgagym.security.access.interceptor.AccessManagerDelegateInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class InterceptorChainConfig(
    private val locationHeaderManager: LocationHeaderManager,
    private val accessManager: AccessManager
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(
            LocationHeaderInterceptor(
                locationHeaderManager
            )
        ).addPathPatterns("/**")

        registry.addInterceptor(
            AccessManagerDelegateInterceptor(
                accessManager
            )
        ).addPathPatterns("/**")
    }
}
