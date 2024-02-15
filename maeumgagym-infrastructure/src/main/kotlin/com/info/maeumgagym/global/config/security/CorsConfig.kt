package com.info.maeumgagym.global.config.security

import com.info.maeumgagym.global.env.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig(
    private val securityProperty: SecurityProperties
) {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf(securityProperty.frontDomain, securityProperty.backDomain)
            allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD")
            allowCredentials = true
            addAllowedHeader("*")
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}
