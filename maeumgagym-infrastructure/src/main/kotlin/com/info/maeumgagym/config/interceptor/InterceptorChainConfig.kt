package com.info.maeumgagym.config.interceptor

import com.info.maeumgagym.response.locationheader.LocationHeaderInterceptor
import com.info.maeumgagym.security.filter.RoleAuthenticationInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class InterceptorChainConfig(
    private val locationHeaderInterceptor: LocationHeaderInterceptor,
    private val roleAuthenticationInterceptor: RoleAuthenticationInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(locationHeaderInterceptor).addPathPatterns("/**")
        registry.addInterceptor(roleAuthenticationInterceptor).addPathPatterns("/**")
    }
}
