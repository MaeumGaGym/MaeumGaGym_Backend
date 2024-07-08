package com.info.maeumgagym.security.authentication.provider

import com.info.maeumgagym.security.authentication.vo.UserModelAuthentication

/**
 * [SecurityContext][org.springframework.security.core.context.SecurityContext]에 대한 접근을 추상화한 클래스
 *
 * @author Daybreak312
 * @since 10-05-2024 AuthenticationManager - Now.
 * @since 20-03-2024 AuthenticationProvider - Authentication Factory.
 */
interface AuthenticationManager {

    /**
     * 현재 등록된 [UserModelAuthentication] 반환
     *
     * 만약 등록된 [UserModelAuthentication]의 [user][UserModelAuthentication.user]가 null인 경우 로드된 [UserModelAuthentication]로 대체하여 반환
     *
     * @return 현재 등록된 [UserModelAuthentication]에서 [user][UserModelAuthentication.user]가 로드된 것 혹은 null
     */
    fun getAuthentication(): UserModelAuthentication?

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
    fun setUserNotLoadedAuthentication(username: String)

    /**
     * 등록된 [UserModelAuthentication] 제거
     */
    fun clear()
}
