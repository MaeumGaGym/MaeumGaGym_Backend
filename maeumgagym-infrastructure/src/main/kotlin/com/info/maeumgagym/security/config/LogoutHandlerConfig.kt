package com.info.maeumgagym.security.config

import com.info.maeumgagym.security.handler.CustomLogoutHandler
import com.info.maeumgagym.security.handler.CustomSuccessLogoutHandler
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.stereotype.Component

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
