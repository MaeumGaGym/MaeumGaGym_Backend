package com.info.maeumgagym.security.authentication

import com.info.maeumgagym.security.authentication.vo.UserModelAuthentication

interface UserModelAuthenticationProvider : AuthenticationProvider {

    /**
     * 생성한 [UserModelAuthentication]의 [UserModelAuthentication.user]를 [subject]를 기반으로 유저를 탐색해 삽입 후 반환
     */
    override fun getAuthentication(subject: String): UserModelAuthentication

    /**
     * [UserModelAuthentication.user]가 비어있는 [UserModelAuthentication] 반환
     */
    fun getEmptyAuthentication(subject: String): UserModelAuthentication
}
