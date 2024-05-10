package com.info.maeumgagym.infrastructure.security.authentication.impl

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.core.user.port.out.ReadUserPort
import com.info.maeumgagym.security.authentication.provider.UserModelAuthenticationManager
import com.info.maeumgagym.security.authentication.vo.UserModelAuthentication
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class UserModelAuthenticationManagerImpl(
    private val readUserPort: ReadUserPort
) : UserModelAuthenticationManager {

    override fun getAuthentication(subject: String): UserModelAuthentication {
        // Authentication에 subject를 넣어 반환
        return UserModelAuthentication(
            userSubject = subject,
            user = readUserPort.readByOAuthId(subject)
                ?: throw CriticalException(
                    "User Not Found with Subject in AccessToken at ${this::class.simpleName}"
                )
        )
    }

    override fun getEmptyAuthentication(subject: String): UserModelAuthentication =
        UserModelAuthentication(
            userSubject = subject,
            user = null
        )
}
