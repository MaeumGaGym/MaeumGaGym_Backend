package com.info.maeumgagym.security.authentication

import com.info.maeumgagym.security.authentication.vo.UserModelAuthentication

interface UserModelAuthenticationProvider : AuthenticationProvider {

    override fun getAuthentication(subject: String): UserModelAuthentication

    fun getEmptyAuthentication(subject: String): UserModelAuthentication
}
