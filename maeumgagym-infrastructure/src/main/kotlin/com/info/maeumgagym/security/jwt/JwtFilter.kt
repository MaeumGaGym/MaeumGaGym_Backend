package com.info.maeumgagym.security.jwt

import com.info.maeumgagym.security.config.RequestPermitConfig
import com.info.maeumgagym.security.jwt.env.JwtProperties
import com.info.maeumgagym.user.model.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 서버의 커스텀 인증 Filter.
 *
 * 헤더를 통해 전해진 AccessToken의 유효성을 검증하고, 이에 따른 인가 작업을 진행
 *
 * @author Daybreak312, kanghyuk
 */
class JwtFilter(
    private val jwtResolver: JwtResolver,
    private val authenticationProvider: AuthenticationProvider,
    private val jwtProperties: JwtProperties
) : OncePerRequestFilter() {

    companion object {
        // 현재 요청에서 인증한 User를 전역적으로 저장, 필요 여부에 따라 Nullable
        internal var authenticatedUser: ThreadLocal<User>? = null
    }

    private var antPathMatcher: AntPathMatcher = AntPathMatcher()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            // 헤더에 토큰이 존재하는지 확인
            val header = request.getHeader(jwtProperties.header)

            if (header != null) {
                // 토큰이 유효한지 확인, 유효하다면 ->
                jwtResolver(header)?.let {
                    // security context holder에 Authentication 저장
                    SecurityContextHolder.getContext().authentication =
                        if (needRole(request)) { // Role 인증이 필요하다면 User Loading
                            authenticationProvider.getAuthentication(it)
                        } else { // 필요하지 않다면 User Lazy Loading
                            authenticationProvider.getEmptyAuthentication(it)
                        }
                }
            }

            // 다음 필터로 넘기기
            filterChain.doFilter(request, response)
        } finally {
            // Filter가 종료되면 User 정보를 초기화
            authenticatedUser = null
        }
    }

    private fun needRole(request: HttpServletRequest): Boolean {
        RequestPermitConfig.needAdminRoleURIs.forEach {
            if (request.method == it.value.name &&
                antPathMatcher.match(it.key, request.requestURI)
            ) {
                return true
            }
        }
        return false
    }
}
