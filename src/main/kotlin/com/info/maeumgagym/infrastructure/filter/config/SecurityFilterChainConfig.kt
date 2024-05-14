package com.info.maeumgagym.infrastructure.filter.config

import com.info.maeumgagym.infrastructure.error.filter.ErrorLogResponseFilter
import com.info.maeumgagym.infrastructure.error.filter.ExceptionConvertFilter
import com.info.maeumgagym.security.authentication.provider.AuthenticationManager
import com.info.maeumgagym.security.mgtoken.MaeumgagymTokenResolver
import com.info.maeumgagym.security.mgtoken.filter.MaeumgagymTokenAuthenticateFilter
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutFilter
import org.springframework.security.web.context.SecurityContextHolderFilter
import org.springframework.stereotype.Component

/**
 * [SecurityFilterChain][org.springframework.security.web.SecurityFilterChain] 속 Filter들의 삽입과 순서 설정
 *
 * @see ApplicationFilterChainConfig
 *
 * @author Daybreak312
 * @since 15-02-2024
 */
@Component
class SecurityFilterChainConfig(
    private val maeumgagymTokenResolver: MaeumgagymTokenResolver,
    private val authenticationManager: AuthenticationManager
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    /**
     * [ErrorLogResponseFilter] -> [ExceptionConvertFilter] -> [SecurityContextHolderFilter] -> [MaeumgagymTokenAuthenticateFilter] -> [LogoutFilter] -> ...
     *
     * @author Daybreak312
     * @since 12-03-2024
     */
    override fun configure(builder: HttpSecurity) {
        builder.run {
            addFilterBefore(
                MaeumgagymTokenAuthenticateFilter(
                    maeumgagymTokenResolver,
                    authenticationManager
                ),
                LogoutFilter::class.java
            )
        }
    }
}
