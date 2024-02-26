package com.info.maeumgagym.global.config.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.stereotype.Component

@Component
class SecurityLogConfig {
    @Value("\${spring.websecurity.debug:false}")
    var webSecurityDebug: Boolean = false

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity -> web.debug(webSecurityDebug) }
    }
}
