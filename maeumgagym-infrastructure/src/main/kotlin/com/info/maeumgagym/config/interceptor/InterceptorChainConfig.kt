package com.info.maeumgagym.config.interceptor

import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.collection.AnnotationBeanCollection
import com.info.maeumgagym.controller.common.locationheader.LocationHeaderManager
import com.info.maeumgagym.response.locationheader.LocationHeaderInterceptor
import com.info.maeumgagym.security.authentication.interceptor.RoleAuthenticationInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class InterceptorChainConfig(
    private val locationHeaderManager: LocationHeaderManager,
    private val annotationBeanCollection: AnnotationBeanCollection,
    private val readCurrentUserPort: ReadCurrentUserPort
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(
            LocationHeaderInterceptor(
                locationHeaderManager
            )
        ).addPathPatterns("/**")

        registry.addInterceptor(
            RoleAuthenticationInterceptor(
                annotationBeanCollection,
                readCurrentUserPort
            )
        ).addPathPatterns("/**")
    }
}
