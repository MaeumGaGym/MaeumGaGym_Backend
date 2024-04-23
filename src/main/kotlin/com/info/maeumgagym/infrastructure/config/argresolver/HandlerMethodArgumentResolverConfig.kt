package com.info.maeumgagym.config.argresolver

import com.info.maeumgagym.security.authentication.resolver.AuthenticationHandlerMethodArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class HandlerMethodArgumentResolverConfig(
    private val authenticationHandlerMethodArgumentResolver: AuthenticationHandlerMethodArgumentResolver
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authenticationHandlerMethodArgumentResolver)
    }
}
