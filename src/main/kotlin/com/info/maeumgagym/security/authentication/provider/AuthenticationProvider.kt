package com.info.maeumgagym.security.authentication.provider

import org.springframework.security.core.Authentication

/**
 * [Authentication] 객체를 생성해주는 클래스
 *
 * 기본 구현체 : [UserModelAuthenticationProvider]
 *
 * @author Daybreak312
 * @since 20-03-2024
 */
interface AuthenticationProvider {

    /**
     * 기본 구현체로부터 [Authentication]을 반환
     */
    fun getAuthentication(subject: String): Authentication
}
