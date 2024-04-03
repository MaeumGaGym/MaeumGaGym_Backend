package com.info.maeumgagym.security.authentication.impl

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.security.authentication.UserModelAuthenticationProvider
import com.info.maeumgagym.security.authentication.vo.UserModelAuthentication
import com.info.maeumgagym.security.jwt.JwtFilter
import org.springframework.stereotype.Component

@Component
class UserModelAuthenticationProviderImpl : UserModelAuthenticationProvider {

    override fun getAuthentication(subject: String): UserModelAuthentication {
        // Authentication에 subject를 넣어 반환
        return UserModelAuthentication(
            userSubject = subject,
            user = JwtFilter.authenticatedUser.get() ?: throw CriticalException(500, "User initialized But It's Null")
        )
    }

    override fun getEmptyAuthentication(subject: String): UserModelAuthentication =
        UserModelAuthentication(
            userSubject = subject,
            user = null
        )
}
