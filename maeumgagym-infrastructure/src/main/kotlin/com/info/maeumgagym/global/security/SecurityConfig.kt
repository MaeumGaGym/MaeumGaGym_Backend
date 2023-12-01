package com.info.maeumgagym.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.global.jwt.JwtAdapter
import com.info.maeumgagym.global.jwt.JwtResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsUtils

@Configuration
class SecurityConfig(
    private val objectMapper: ObjectMapper,
    private val jwtResolver: JwtResolver,
    private val jwtAdapter: JwtAdapter
) {
    @Bean
    protected fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http.csrf()
            .disable()
            .cors()
            .and()
            .formLogin()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

            .authorizeRequests()
            .requestMatchers(CorsUtils::isCorsRequest)
            .permitAll()
            .antMatchers(HttpMethod.POST, "/google/login").permitAll()
            .antMatchers(HttpMethod.POST, "/kakao/login").permitAll()
            .antMatchers(HttpMethod.POST, "/apple/login").permitAll()
            .anyRequest()
            .authenticated().and()

            .apply(FilterConfig(objectMapper, jwtResolver, jwtAdapter)).and().build()
}
