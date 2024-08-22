package com.info.maeumgagym.infrastructure.config.argresolver

import com.info.maeumgagym.infrastructure.processor.annotation.WebAdapterMethodArgumentAnnotationResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class HandlerMethodArgumentResolverConfig(
    private val webAdapterMethodArgumentAnnotationResolver: WebAdapterMethodArgumentAnnotationResolver
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(webAdapterMethodArgumentAnnotationResolver)
    }
}
