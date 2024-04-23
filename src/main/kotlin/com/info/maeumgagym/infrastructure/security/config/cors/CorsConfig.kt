package com.info.maeumgagym.infrastructure.security.config.cors

import com.info.maeumgagym.infrastructure.security.env.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/**
 * CORS 관련 설정
 *
 * @author gurdl0525
 * @since 16-02-2024
 */
@Configuration
class CorsConfig(private val securityProperty: SecurityProperties) {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf(
                securityProperty.frontDomain,
                securityProperty.backDomain,
                "https://maeumgagym-main-stag.xquare.app",
                "https://maeumgagym-user-stag.xquare.app",
                "https://maeumgagym-admin-stag.xquare.app",
                "http://localhost:3000"
            )
            allowedMethods = listOf("*")
            allowCredentials = true
            addAllowedHeader("*")
            exposedHeaders = mutableListOf("Authorization", "authorization")
            maxAge = 1800L
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}
