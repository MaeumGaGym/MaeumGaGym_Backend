package com.info.maeumgagym.filter.config

import com.info.maeumgagym.error.filter.ErrorLogResponseFilter
import com.info.maeumgagym.error.filter.ExceptionConvertFilter
import com.info.maeumgagym.security.jwt.AuthenticationProvider
import com.info.maeumgagym.security.jwt.JwtFilter
import com.info.maeumgagym.security.jwt.JwtResolver
import com.info.maeumgagym.security.jwt.env.JwtProperties
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutFilter
import org.springframework.security.web.context.SecurityContextHolderFilter
import org.springframework.stereotype.Component

@Component
class SecurityFilterChainConfig(
    private val jwtResolver: JwtResolver,
    private val jwtProperties: JwtProperties,
    private val authenticationProvider: AuthenticationProvider
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    /**
     * [ErrorLogResponseFilter] -> [ExceptionConvertFilter] -> [SecurityContextHolderFilter] -> [JwtFilter] -> [LogoutFilter] -> ...
     *
     * @author Daybreak312
     * @since 12-03-2024
     */
    override fun configure(builder: HttpSecurity) {
        builder.run {
            addFilterBefore(
                JwtFilter(jwtResolver, authenticationProvider, jwtProperties),
                LogoutFilter::class.java
            )
        }
    }
}
