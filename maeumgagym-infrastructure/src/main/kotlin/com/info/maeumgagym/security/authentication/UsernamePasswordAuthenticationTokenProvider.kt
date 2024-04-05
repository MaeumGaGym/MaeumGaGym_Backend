package com.info.maeumgagym.security.authentication

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

/**
 * [UsernamePasswordAuthenticationToken] 객체를 생성해주는 클래스
 *
 * @author Daybreak312
 * @since 02-04-2024
 */
interface UsernamePasswordAuthenticationTokenProvider : AuthenticationProvider {

    /**
     * 생성한 [UsernamePasswordAuthenticationToken]의 [org.springframework.security.core.Authentication.getPrincipal]에 User 정보를 담아 반환
     */
    override fun getAuthentication(subject: String): UsernamePasswordAuthenticationToken

    /**
     * 생성한 [UsernamePasswordAuthenticationToken]의 [org.springframework.security.core.Authentication.getPrincipal]을 null로 생성해 반환
     */
    fun getEmptyAuthentication(subject: String): UsernamePasswordAuthenticationToken
}
