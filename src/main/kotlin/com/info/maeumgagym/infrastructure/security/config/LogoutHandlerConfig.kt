package com.info.maeumgagym.infrastructure.security.config

import com.info.maeumgagym.infrastructure.security.handler.CustomLogoutHandler
import com.info.maeumgagym.infrastructure.security.handler.CustomSuccessLogoutHandler
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.stereotype.Component

/**
 * Logout 기능을 [LogoutFilter][org.springframework.security.web.authentication.logout.LogoutFilter]가 수행하도록, [LogoutHandler][org.springframework.security.web.authentication.logout.LogoutHandler]와 [LogoutSuccessHandler][org.springframework.security.web.authentication.logout.LogoutSuccessHandler]를 명시해주는 설정
 *
 * @author Daybreak312
 * @since 11-03-2024
 */
@Component
class LogoutHandlerConfig(
    private val customLogoutHandler: CustomLogoutHandler,
    private val customSuccessLogoutHandler: CustomSuccessLogoutHandler
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        builder
            .logout()
            .logoutUrl("/auth/logout")
            .addLogoutHandler(customLogoutHandler)
            .logoutSuccessHandler(customSuccessLogoutHandler)
    }
}
