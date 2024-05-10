package com.info.maeumgagym.security.config

import com.info.maeumgagym.infrastructure.error.handler.CustomAccessDeniedHandler
import com.info.maeumgagym.infrastructure.error.handler.CustomAuthenticationEntryPoint
import com.info.maeumgagym.infrastructure.filter.config.SecurityFilterChainConfig
import com.info.maeumgagym.security.env.CSRFProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

/**
 * SpringSecurity와 관련된 설정들의 최상위 설정
 *
 * API 별 인증 권한에 대한 설정은 Controller에서 아래 어노테이션에 의해 정의됨
 * - [RequireRole][com.info.maeumgagym.common.annotation.security.RequireRole]
 * - [RequireAuthentication][com.info.maeumgagym.common.annotation.security.RequireAuthentication]
 * - [Permitted][com.info.maeumgagym.common.annotation.security.Permitted]
 *
 * 각 설정은 [AccessManagerDelegateInterceptor][com.info.maeumgagym.security.access.interceptor.AccessManagerDelegateInterceptor], [AuthenticationHandlerMethodArgumentResolver][com.info.maeumgagym.security.authentication.resolver.AuthenticationHandlerMethodArgumentResolver]에 의해 구현
 *
 * @see LogoutHandlerConfig
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
            .authorizeRequests().anyRequest().permitAll().and() // 어노테이션으로의 책임 인가
//
            .exceptionHandling() // 인증 관련 예외에 관한 설정들
            .accessDeniedHandler(accessDeniedHandler) // 인증 실패시 발생하는 예외에 대한 커스텀 예외로의 핸들링
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
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
