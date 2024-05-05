package com.info.maeumgagym.security.mgtoken.filter

import com.info.maeumgagym.security.authentication.provider.UserModelAuthenticationProvider
import com.info.maeumgagym.security.config.RequestPermitConfig
import com.info.maeumgagym.security.mgtoken.MaeumgagymTokenResolver
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 서버의 커스텀 인증 Filter.
 *
 * 헤더를 통해 전해진 AccessToken의 유효성을 검증
 *
 * [MaeumgagymTokenResolver]를 통해 [MaeumgagymTokenDecoder][com.info.maeumgagym.security.mgtoken.MaeumgagymTokenDecoder], [MaeumgagymTokenValidator][com.info.maeumgagym.security.mgtoken.MaeumgagymTokenValidator]를 사용
 *
 * @author Daybreak312
 */
class MaeumgagymTokenAuthenticateFilter(
    private val maeumgagymTokenResolver: MaeumgagymTokenResolver,
    private val authenticationProvider: UserModelAuthenticationProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 헤더에 토큰이 존재하는지 확인
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (header != null) {
            // 토큰이 유효한지 확인, 유효하다면 ->
            maeumgagymTokenResolver(header)?.let {
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
