package com.info.maeumgagym.security.authentication.provider

import org.springframework.security.core.Authentication

/**
 * [SecurityContext][org.springframework.security.core.context.SecurityContext] 내부의 [Authentication]를 관리하는 클래스
 *
 * 외부로부터 username만 받고, 내부에서 [Authentication] 객체를 생성해 관리
 *
 * 기본 구현체 : [UserModelAuthenticationManager]
 *
 * @author Daybreak312
 * @since 10-05-2024 AuthenticationManager - Now.
 * @since 20-03-2024 AuthenticationProvider - Authentication Factory.
 */
interface AuthenticationManager {

    /**
     * 현재 등록된 [Authentication] 혹은 새로 생성한 객체을 반환
     *
     * [SecurityContext][org.springframework.security.core.context.SecurityContext]가 비어있을 경우 새로 생성
     *
     * @return 현재 등록되어있거나 새로 생성한 [Authentication]
     */
    fun getAuthentication(username: String): Authentication?

    /**
     * 현재 등록된 [Authentication]을 반환
     *
     * 만약 [SecurityContext][org.springframework.security.core.context.SecurityContext]가 비어있을 경우 예외 발생
     *
     * @return 현재 등록된 [Authentication]
     * @throws com.info.maeumgagym.common.exception.AuthenticationException 객체가 없는 경우
     */
    fun getAuthenticationNotNull(username: String): Authentication

    /**
     * 새로운 [Authentication]을 등록
     *
     * username으로 검색한 유저의 정보로 [Authentication] 생성
     */
    fun setAuthentication(username: String)

    /**
     * 새로운 [Authentication]을 등록
     *
     * [Authentication] 구현체에 직접 의존하기 때문에, username을 기반으로 등록하는 [setAuthentication]을 사용할 것을 권고
     */
    fun setAuthentication(authentication: Authentication)
}
