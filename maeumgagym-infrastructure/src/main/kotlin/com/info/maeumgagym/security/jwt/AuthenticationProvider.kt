package com.info.maeumgagym.security.jwt

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

/**
 * [UsernamePasswordAuthenticationToken] 객체를 생성해주는 클래스
 *
 * @author Daybreak312
 * @since 20-03-2024
 */
interface AuthenticationProvider {

    /**
     * 생성하는 [UsernamePasswordAuthenticationToken]의 [UsernamePasswordAuthenticationToken.principal] (AuthDetails)에 User를 조회에 User 정보를 담아 반환
     */
    fun getAuthentication(subject: String): UsernamePasswordAuthenticationToken

    /**
     * 생성하는 [UsernamePasswordAuthenticationToken]의 [UsernamePasswordAuthenticationToken.principal] (AuthDetails)에 User를 null로 삽입해 반환
     */
    fun getEmptyAuthentication(subject: String): UsernamePasswordAuthenticationToken
}
