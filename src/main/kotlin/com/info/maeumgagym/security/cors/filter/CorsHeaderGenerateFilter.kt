package com.info.maeumgagym.security.cors.filter

import org.springframework.http.HttpHeaders
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CorsHeaderGenerateFilter : GenericFilterBean() {

    private val accessControlAllowHeaders = listOf(
        "Authorization",
        "authorization",
        "OAUTH-TOKEN",
        "Oauth-Token",
        "oauth-token"
    )

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        chain.doFilter(request, response)

        doFilterInternal(request as HttpServletRequest, response as HttpServletResponse, chain)
    }

    private fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val newHeader = generateCorsHeader(response)

        response.addHeader(
            HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,
            newHeader
        )
    }

    private fun generateCorsHeader(response: HttpServletResponse): String {
        val originalCorsHeader = response.getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS)

        return originalCorsHeader?.plus(
            accessControlAllowHeaders.joinToString(",", ",")
        ) ?: accessControlAllowHeaders.joinToString(",")
    }
}
