package com.info.maeumgagym.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.global.error.GlobalExceptionFilter
import com.info.maeumgagym.global.jwt.JwtFilter
import com.info.maeumgagym.global.jwt.JwtAdapter
import com.info.maeumgagym.global.jwt.JwtResolver
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
class FilterConfig(
    private val objectMapper: ObjectMapper,
    private val jwtResolver: JwtResolver,
    private val jwtAdapter: JwtAdapter
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        builder.addFilterBefore(JwtFilter(jwtResolver, jwtAdapter), UsernamePasswordAuthenticationFilter::class.java)
        builder.addFilterBefore(GlobalExceptionFilter(objectMapper), JwtFilter::class.java)
    }
}
