package com.info.maeumgagym.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.global.error.GlobalExceptionFilter
import com.info.maeumgagym.global.security.jwt.JwtFilter
import com.info.maeumgagym.global.security.principle.CustomUserDetailService
import com.info.maeumgagym.global.security.jwt.JwtAdapter
import com.info.maeumgagym.global.security.token.JwtResolver
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
class FilterConfig(
    private val objectMapper: ObjectMapper,
    private val customUserDetailService: CustomUserDetailService,
    private val jwtResolver: JwtResolver,
    private val jwtAdapter: JwtAdapter
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    override fun configure(builder: HttpSecurity) {
        builder.addFilterBefore(
            JwtFilter(customUserDetailService, jwtResolver, jwtAdapter),
            UsernamePasswordAuthenticationFilter::class.java
        )
        builder.addFilterBefore(GlobalExceptionFilter(objectMapper), JwtFilter::class.java)
    }
}
