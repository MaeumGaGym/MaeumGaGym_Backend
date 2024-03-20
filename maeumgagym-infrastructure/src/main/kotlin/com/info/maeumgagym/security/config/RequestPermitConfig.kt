package com.info.maeumgagym.security.config

import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsUtils

@Component
class RequestPermitConfig : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    internal companion object {
        val permittedURI = mapOf<HttpMethod, String>(
            Pair(HttpMethod.POST, "/**/signup"),
            Pair(HttpMethod.GET, "/**/login"),
            Pair(HttpMethod.PUT, "/**/recovery"),
            Pair(HttpMethod.GET, "/auth/nickname/*"),
            Pair(HttpMethod.GET, "/auth/re-issue"),
            Pair(HttpMethod.GET, "/public/csrf"),
            Pair(HttpMethod.GET, "/actuator/health")
        )

        val needAdminRoleURI = mapOf<HttpMethod, String>(
            Pair(HttpMethod.GET, "/report")
        )
    }

    override fun configure(builder: HttpSecurity) {
        builder.authorizeRequests().run {
            requestMatchers(CorsUtils::isCorsRequest).permitAll()
            permittedURIConfigure()
            needAdminRoleURIConfigure()
            anyRequest().authenticated()
        }
    }

    private fun ExpressionUrlAuthorizationConfigurer<HttpSecurity>
    .ExpressionInterceptUrlRegistry.permittedURIConfigure() {
        permittedURI.forEach {
            antMatchers(it.key, it.value).permitAll()
        }
    }

    private fun ExpressionUrlAuthorizationConfigurer<HttpSecurity>
    .ExpressionInterceptUrlRegistry.needAdminRoleURIConfigure() {
        permittedURI.forEach {
            antMatchers(it.key, it.value).permitAll()
        }
    }
}
