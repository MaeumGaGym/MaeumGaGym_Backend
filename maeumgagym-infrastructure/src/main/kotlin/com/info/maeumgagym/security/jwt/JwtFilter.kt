package com.info.maeumgagym.security.jwt

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(
    private val jwtResolver: JwtResolver,
    private val jwtAdapter: JwtAdapter
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        jwtResolver.resolveToken(request)?.let { // WHEN : 헤더에 토큰이 존재한다면
            // security context holder에 user저장
            SecurityContextHolder.getContext().authentication = jwtAdapter.getAuthentication(it)
        }
        // 다음 필터로 넘기기
        filterChain.doFilter(request, response)
    }
}
