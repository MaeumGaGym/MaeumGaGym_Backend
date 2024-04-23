package com.info.maeumgagym.infrastructure.security.jwt

import com.info.maeumgagym.infrastructure.security.authentication.provider.UserModelAuthenticationProvider
import com.info.maeumgagym.infrastructure.security.config.RequestPermitConfig
import com.info.maeumgagym.infrastructure.security.jwt.env.JwtProperties
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 서버의 커스텀 인증 Filter.
 *
 * 헤더를 통해 전해진 AccessToken의 유효성을 검증하고, 이에 따른 인가 작업을 진행
 *
 * Request의 [Role][com.info.maeumgagym.core.user.model.Role] 인증 필요 여부에 따라 [User][com.info.maeumgagym.core.user.model.User]를 이 곳에서 미리 불러오거나, 이후 비즈니스 로직 실행 도중 [User][com.info.maeumgagym.core.user.model.User] 정보가 필요하다면 [ReadCurrentUserPort][com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort]에서 Lazy Loading
 *
 * @author Daybreak312, kanghyuk
 */
class JwtFilter(
    private val jwtResolver: JwtResolver,
    private val authenticationProvider: UserModelAuthenticationProvider,
    private val jwtProperties: JwtProperties
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 헤더에 토큰이 존재하는지 확인
        val header = request.getHeader(jwtProperties.header)

        if (header != null) {
            // 토큰이 유효한지 확인, 유효하다면 ->
            jwtResolver(header)?.let {
                // security context holder에 Authentication 저장
                SecurityContextHolder.getContext().authentication =
                    if (needRole(request)) { // Role 인증이 필요하다면 User Loading
                        authenticationProvider.getAuthentication(it)
                    } else { // 필요하지 않다면 User Lazy Loading 사용
                        authenticationProvider.getEmptyAuthentication(it)
                    }
            }
        }

        // 다음 필터로 넘기기
        filterChain.doFilter(request, response)
    }

    private fun needRole(request: HttpServletRequest): Boolean {
        RequestPermitConfig.needAdminRoleURIs.forEach {
            if (request.requestURI == it.key &&
                request.method == it.value.name
            ) {
                return true
            }
        }
        return false
    }
}
