package com.info.maeumgagym.security.config

import com.info.maeumgagym.user.model.Role
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
        val permittedURIs: Map<String, HttpMethod> = mapOf(
            Pair("/**/signup", HttpMethod.POST),
            Pair("/**/login", HttpMethod.GET),
            Pair("/**/recovery", HttpMethod.PUT),
            Pair("/auth/nickname/*", HttpMethod.GET),
            Pair("/auth/re-issue", HttpMethod.GET),
            Pair("/public/csrf", HttpMethod.GET),
            Pair("/actuator/health", HttpMethod.GET)
        )

        val needAdminRoleURIs: Map<String, HttpMethod> = mapOf(
            Pair("/report", HttpMethod.GET)
        )
    }

    override fun configure(builder: HttpSecurity) {
        builder.authorizeRequests()
            .requestMatchers(CorsUtils::isCorsRequest).permitAll()
            .permittedURIConfigure()
            .needAdminRoleURIConfigure()
            .anyRequest().authenticated()
    }

    private fun ExpressionUrlAuthorizationConfigurer<HttpSecurity>
    .ExpressionInterceptUrlRegistry.permittedURIConfigure() =
        this.apply {
            permittedURIs.forEach {
                antMatchers(it.value, it.key).permitAll()
            }
        }

    private fun ExpressionUrlAuthorizationConfigurer<HttpSecurity>
    .ExpressionInterceptUrlRegistry.needAdminRoleURIConfigure() =
        this.apply {
            needAdminRoleURIs.forEach {
                antMatchers(it.value, it.key).hasRole(Role.ADMIN.name)
            }
        }
}
