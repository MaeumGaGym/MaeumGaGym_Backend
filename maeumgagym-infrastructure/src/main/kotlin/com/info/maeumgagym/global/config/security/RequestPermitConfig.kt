package com.info.maeumgagym.global.config.security

import com.info.maeumgagym.user.model.Role
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsUtils

@Component
class RequestPermitConfig : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        builder.authorizeRequests().run {
            requestMatchers(CorsUtils::isCorsRequest).permitAll()
            antMatchers(HttpMethod.POST, "/**/signup").permitAll()
            antMatchers(HttpMethod.GET, "/**/login").permitAll()
            antMatchers(HttpMethod.PUT, "/**/recovery").permitAll()
            antMatchers(HttpMethod.GET, "/auth/nickname/*").permitAll()
            antMatchers(HttpMethod.GET, "/auth/re-issue").permitAll()
            antMatchers(HttpMethod.GET, "/public/csrf").permitAll()
            antMatchers(HttpMethod.GET, "/report").hasRole(Role.ADMIN.name)
            antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
            anyRequest().authenticated()
        }
    }
}
