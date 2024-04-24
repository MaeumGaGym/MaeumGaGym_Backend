package com.info.maeumgagym.infrastructure.security.config

import com.info.maeumgagym.infrastructure.error.handler.CustomAccessDeniedHandler
import com.info.maeumgagym.infrastructure.error.handler.CustomAuthenticationEntryPoint
import com.info.maeumgagym.infrastructure.filter.config.SecurityFilterChainConfig
import com.info.maeumgagym.infrastructure.security.env.CSRFProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

/**
 * SpringSecurity와 관련된 설정들의 최상위 설정
 *
 * @see LogoutHandlerConfig
 * @see RequestPermitConfig
 * @see SecurityFilterChainConfig
 * @see SecurityLogConfig
 * @see com.info.maeumgagym.security.config.cors.CorsConfig
 *
 * @author Daybreak312, gurdl0525, HyunSu1768
 * @since 20-11-2023
 */
@Configuration
class SecurityConfig(
    private val csrfProperties: CSRFProperties,
    private val requestPermitConfig: RequestPermitConfig,
    private val securityFilterChainConfig: SecurityFilterChainConfig,
    private val accessDeniedHandler: CustomAccessDeniedHandler,
    private val authenticationEntryPoint: CustomAuthenticationEntryPoint,
    private val logoutHandlerConfig: LogoutHandlerConfig
) {

    internal companion object {
        lateinit var securityFilterChain: SecurityFilterChain
            protected set
    }

    @Bean
    protected fun filterChain(http: HttpSecurity): SecurityFilterChain {
        securityFilterChain = http
            .apply(logoutHandlerConfig::configure)
            .formLogin().disable() // Html Form 로그인 비활성화
//            .csrf().csrfTokenRepository(getCsrfTokenRepository()).and() // CSRF 설정 (temporary disuse)
            .csrf().disable()
            .cors().and() // CORS 활성화
            //.requiresChannel().anyRequest().requiresSecure().and() // XSS Attack (HTTPS 요청 요구) local test시 주석 처리할 것
//
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//
            .exceptionHandling() // 인증 관련 예외에 관한 설정들
            .accessDeniedHandler(accessDeniedHandler) // 인증 실패시 발생하는 예외에 대한 커스텀 예외로의 핸들링
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
//
            .apply(requestPermitConfig::configure) // 매핑에 따른 인증이 필요한지에 대한 설정
//
            .headers().frameOptions().sameOrigin()
            .and()
//
            .apply(securityFilterChainConfig).and() // SecurityFilterChain에 대한 설정
            .build()

        return securityFilterChain
    }

    private fun getCsrfTokenRepository(): CookieCsrfTokenRepository =
        CookieCsrfTokenRepository().apply {
            setSecure(true)
            setCookieName(csrfProperties.cookie)
            setHeaderName(csrfProperties.header)
            setCookieHttpOnly(true)
            setParameterName(csrfProperties.parameter)
        }
}