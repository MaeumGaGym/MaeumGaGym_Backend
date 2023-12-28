package com.info.maeumgagym.global.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.info.maeumgagym.global.env.security.SecurityProperties
import com.info.maeumgagym.global.jwt.JwtAdapter
import com.info.maeumgagym.global.jwt.JwtResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig(
    private val objectMapper: ObjectMapper,
    private val jwtResolver: JwtResolver,
    private val jwtAdapter: JwtAdapter,
    private val property: SecurityProperties
) {
    @Bean
    protected fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http.csrf().disable()
            .formLogin().disable()

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

            .authorizeRequests()
            .requestMatchers(CorsUtils::isCorsRequest)
            .permitAll()

            .antMatchers(HttpMethod.POST, "/maeumgagym/google/signup").permitAll()
            .antMatchers(HttpMethod.POST, "/maeumgagym/google/login").permitAll()
            .antMatchers(HttpMethod.POST, "/maeumgagym/kakao/login").permitAll()
            .antMatchers(HttpMethod.POST, "/maeumgagym/kakao/signup").permitAll()
            .antMatchers(HttpMethod.POST, "/maeumgagym/apple/login").permitAll()
            .antMatchers("/swagger-ui/**", "/docs/**").permitAll()
            .antMatchers("/maeumgagym/swagger-ui/**", "/maeumgagym/docs/**").permitAll()
            .anyRequest().authenticated()
            .and()

            .cors().and()
            .exceptionHandling()

            .and()
            .headers()
            .frameOptions()
            .sameOrigin()

            .and()
            .apply(FilterConfig(objectMapper, jwtResolver, jwtAdapter))

            .and().build()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {

        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf(property.backLocal, property.backDomain, property.frontLocal)
            allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD")
            allowCredentials = true
            addAllowedHeader("*")
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}
