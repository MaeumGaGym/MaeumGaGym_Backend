package com.info.maeumgagym.security.authentication.token

import com.info.maeumgagym.security.authentication.token.vo.AuthenticationToken
import javax.servlet.http.HttpServletRequest

/**
 * 복호화되어 [AuthenticationToken]으로 변환된 인증용 토큰을 검증하는 클래스
 *
 * 암호화된 인증용 토큰을 [AuthenticationTokenDecoder]가 [AuthenticationToken]으로 변환하면, 이 클래스를 통해 검증한 후 객체를 사용할 것
 *
 * @see AuthenticationTokenEncoder
 * @see AuthenticationTokenDecoder
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
interface AuthenticationTokenValidator {

    /**
     * 복호화된 인증용 토큰([AuthenticationToken])의 유효성을 검증
     *
     * 검증하는 유효성 목록
     * - 토큰 발급 대상과 현재 사용자의 일치 여부
     * - 토큰 유효 기간 만료 여부
     *
     * @param authenticationToken 복호화된 인증용 토큰의 VO
     *
     * @throws com.info.maeumgagym.common.exception.SecurityException 무효한 토큰일 경우
     */
    fun validate(authenticationToken: AuthenticationToken, request: HttpServletRequest)
}
