package com.info.maeumgagym.security.authentication.impl

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.security.authentication.UserModelAuthenticationProvider
import com.info.maeumgagym.security.authentication.vo.UserModelAuthentication
import com.info.maeumgagym.security.jwt.JwtFilter
import com.info.maeumgagym.user.port.out.ReadUserPort

class UserModelAuthenticationProviderImpl(
    private val readUserPort: ReadUserPort
) : UserModelAuthenticationProvider {

    override fun getAuthentication(subject: String): UserModelAuthentication {
        // User가 필요한 경우 불러와 전역적으로 저장
        JwtFilter.authenticatedUser = ThreadLocal.withInitial {
            readUserPort.readByOAuthId(subject)
                ?: throw CriticalException(500, "User Not Found In UsernamePasswordAuthenticationTokenProvider")
        }

        // Authentication에 subject를 넣어 반환
        return UserModelAuthentication(
            userSubject = subject,
            user = JwtFilter.authenticatedUser?.get() ?: throw CriticalException(500, "User initialized But It's Null")
        )
    }

    override fun getEmptyAuthentication(subject: String): UserModelAuthentication =
        UserModelAuthentication(
            userSubject = subject,
            user = null
        )
}
