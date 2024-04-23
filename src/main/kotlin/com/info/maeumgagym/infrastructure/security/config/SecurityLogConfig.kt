package com.info.maeumgagym.infrastructure.security.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.stereotype.Component

/**
 * [SecurityFilterChain][org.springframework.security.web.SecurityFilterChain] 관련 Log 작성 설정
 *
 * @author HyunSu1768
 * @since 26-02-2024
 */
@Component
class SecurityLogConfig {
    @Value("\${spring.websecurity.debug:false}")
    var webSecurityDebug: Boolean = false

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity -> web.debug(webSecurityDebug) }
    }
}
