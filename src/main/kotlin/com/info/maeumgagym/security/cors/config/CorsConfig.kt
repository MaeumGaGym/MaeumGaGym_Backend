package com.info.maeumgagym.security.cors.config

import com.info.maeumgagym.security.env.SecurityProperties
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
                "https://maeumgagym.xqaure.app",
                "https://maeumgagym-webview.xqaure.app",
                "http://localhost:3000"
            )
            allowedMethods = listOf("*")
            allowCredentials = true
            addAllowedHeader("*")
            allowedHeaders =
                mutableListOf("*", "Authorization", "authorization", "OAUTH-TOKEN", "Oauth-Token", "oauth-token")
            exposedHeaders =
                mutableListOf("Authorization", "authorization", "OAUTH-TOKEN", "Oauth-Token", "oauth-token")
            maxAge = 1800L
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}
