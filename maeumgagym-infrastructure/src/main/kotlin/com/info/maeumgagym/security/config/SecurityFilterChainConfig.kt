package com.info.maeumgagym.security.config

import com.info.maeumgagym.error.filter.ErrorLogResponseFilter
import com.info.maeumgagym.error.filter.ExceptionConvertFilter
import com.info.maeumgagym.response.writer.DefaultHttpServletResponseWriter
import com.info.maeumgagym.response.writer.ErrorLogHttpServletResponseWriter
import com.info.maeumgagym.security.jwt.JwtFilter
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutFilter
import org.springframework.security.web.context.SecurityContextHolderFilter
import org.springframework.stereotype.Component

@Component
class SecurityFilterChainConfig(
    private val jwtFilter: JwtFilter,
    private val defaultHttpServletResponseWriter: DefaultHttpServletResponseWriter,
    private val errorLogHttpServletResponseWriter: ErrorLogHttpServletResponseWriter
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    /**
     * [ErrorLogResponseFilter] -> [ExceptionConvertFilter] -> [SecurityContextHolderFilter] -> [JwtFilter] -> [LogoutFilter] -> ...
     *
     * @author Daybreak312
     * @since 12-03-2024
     */
    override fun configure(builder: HttpSecurity) {
        builder.run {
            addFilterBefore(jwtFilter, LogoutFilter::class.java)
            addFilterBefore(ExceptionConvertFilter(), SecurityContextHolderFilter::class.java)
            addFilterBefore(
                ErrorLogResponseFilter(defaultHttpServletResponseWriter, errorLogHttpServletResponseWriter),
                ExceptionConvertFilter::class.java
            )
        }
    }
}
