package com.info.maeumgagym.security.authentication.provider

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.security.authentication.vo.UserModelAuthentication

/**
 * [SecurityContext][org.springframework.security.core.context.SecurityContext]와 [UserModelAuthentication]에 대한 접근을 추상화한 클래스
 *
 * @author Daybreak312
 * @since 10-05-2024 AuthenticationManager - Now.
 * @since 20-03-2024 AuthenticationProvider - Authentication Factory.
 */
interface AuthenticationManager {

    /**
     * 현재 등록된 [UserModelAuthentication] 혹은 새로 생성한 객체을 반환
     *
     * [SecurityContext][org.springframework.security.core.context.SecurityContext]가 비어있을 경우 새로 생성
     *
     * @return 현재 등록되어있거나 새로 생성한 [UserModelAuthentication]
     * @throws CriticalException username으로 유저를 찾을 수 없을 경우
     */
    fun getAuthentication(username: String): UserModelAuthentication

    /**
     * 현재 등록된 [UserModelAuthentication]을 반환
     *
     * @return 현재 등록된 [UserModelAuthentication], 없을 경우 null
     */
    fun getAuthenticationOrNull(): UserModelAuthentication?

    /**
     * 새로운 [UserModelAuthentication]을 등록
     *
     * username으로 검색한 유저의 정보로 [UserModelAuthentication] 생성
     */
    fun setAuthentication(username: String)

    /**
     * [UserModelAuthentication.user]가 null인 [UserModelAuthentication]을 등록
     *
     * 이후에 [getAuthentication]을 실행할 때 [UserModelAuthentication.user]가 채워짐
     */
    fun setLazyLoadingAuthentication(username: String)

    /**
     * 새로운 [UserModelAuthentication]을 등록
     *
     * [UserModelAuthentication] 구현체에 직접 의존하기 때문에, username을 기반으로 등록하는 [setAuthentication]을 사용할 것을 권고
     */
    fun setAuthentication(authentication: UserModelAuthentication)
}
