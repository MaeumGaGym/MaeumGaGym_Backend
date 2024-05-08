package com.info.maeumgagym.security.mgtoken.filter

import com.info.maeumgagym.security.authentication.provider.UserModelAuthenticationProvider
import com.info.maeumgagym.security.mgtoken.MaeumgagymTokenResolver
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 클라이언트가 토큰을 제공했을 경우, 이를 해석한 인증 정보를 등록하는 필터
 *
 * 인증 정보를 주입하는 책임만 가진 필터이며, 인증 정보를 보고 접근을 허용하는 인가 작업의 책임은 다른 클래스에게 있음.
 * 따라서, 이 필터에서는 토큰이 없을 경우에 대한 예외 처리를 진행하지 않음
 * 토큰이 존재하지 않을 경우 [org.springframework.security.web.authentication.AnonymousAuthenticationFilter]에서 익명 인증 정보를 주입
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

        // 토큰이 존재하는 경우 => 인증 정보를 등록해야하는 경우
        if (header != null) {
            // 토큰으로부터 username 추출
            val username = maeumgagymTokenResolver.resolveAccessToken(header)

            // 인증 정보 객체를 생성해 SecurityContext에 등록
            SecurityContextHolder.getContext().authentication =
                authenticationProvider.getEmptyAuthentication(username)
        }

        // 다음 필터로 넘기기
        filterChain.doFilter(request, response)
    }
}
