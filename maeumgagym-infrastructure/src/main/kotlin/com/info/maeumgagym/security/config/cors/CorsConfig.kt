package com.info.maeumgagym.security.config.cors

import com.info.maeumgagym.security.env.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

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
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}
