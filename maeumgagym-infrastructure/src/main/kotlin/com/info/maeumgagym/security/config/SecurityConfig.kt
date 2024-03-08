package com.info.maeumgagym.security.config

import com.info.maeumgagym.security.env.CSRFProperties
import com.info.maeumgagym.error.CustomAccessDeniedHandler
import com.info.maeumgagym.error.CustomAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

@Configuration
class SecurityConfig(
    private val csrfProperties: CSRFProperties,
    private val requestPermitConfig: RequestPermitConfig,
    private val securityFilterChainConfig: SecurityFilterChainConfig,
    private val accessDeniedHandler: CustomAccessDeniedHandler,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint
) {
    @Bean
    protected fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .formLogin().disable() // Html Form 로그인 비활성화
//            .csrf().csrfTokenRepository(getCsrfTokenRepository()).and() // CSRF 설정 (temporary disuse)
            .csrf().disable()
            .cors().and() // CORS 활성화
            .requiresChannel().anyRequest().requiresSecure().and() // XSS Attack (HTTPS 요청 요구) local test시 주석 처리할 것
//
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//
            .exceptionHandling() // 인증 관련 예외에 관한 설정들
            .accessDeniedHandler(accessDeniedHandler) // 인증 실패시 발생하는 예외에 대한 커스텀 예외로의 핸들링
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
//
            .apply(requestPermitConfig).and() // 매핑에 따른 인증이 필요한지에 대한 설정
            .authorizeRequests()
            .antMatchers("/swagger-ui/**", "/docs/**").permitAll().and()
//
            .headers().frameOptions().sameOrigin()
            .and()
//
            .apply(securityFilterChainConfig) // SecurityFilterChain에 대한 설정
            .and()
            .build()

    private fun getCsrfTokenRepository(): CookieCsrfTokenRepository =
        CookieCsrfTokenRepository().apply {
            setSecure(true)
            setCookieName(csrfProperties.cookie)
            setHeaderName(csrfProperties.header)
            setCookieHttpOnly(true)
            setParameterName(csrfProperties.parameter)
        }
}
