package com.info.maeumgagym.security.authentication

import org.springframework.security.core.Authentication

/**
 * [org.springframework.security.core.Authentication] 객체를 생성해주는 클래스
 *
 * @author Daybreak312
 * @since 20-03-2024
 */
interface AuthenticationProvider {

    /**
     * 생성한 객체의 [org.springframework.security.core.Authentication.getPrincipal]에 User 정보를 담아 반환
     */
    fun getAuthentication(subject: String): Authentication

    /**
     * 생성한 객체의 [org.springframework.security.core.Authentication.getPrincipal]을 null로 생성해 반환
     */
    fun getEmptyAuthentication(subject: String): Authentication
}
