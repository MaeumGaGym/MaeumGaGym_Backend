package com.info.maeumgagym.security.authentication.provider

import com.info.maeumgagym.security.authentication.vo.UserModelAuthentication

/**
 * [UserModelAuthentication]의 생성을 추상화
 *
 * @author Daybreak312
 * @since 14-05-2024
 */
interface UserModelAuthenticationFactory {

    /**
     * [UserModelAuthentication.user]를 주입한 상태의 [UserModelAuthentication] 반환
     */
    fun createFilledAuthentication(username: String): UserModelAuthentication

    /**
     * [UserModelAuthentication.user]가 비어있는 상태의 [UserModelAuthentication] 반환
     */
    fun createEmptyAuthentication(username: String): UserModelAuthentication
}
