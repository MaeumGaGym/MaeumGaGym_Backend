package com.info.maeumgagym.security.authentication.provider.impl

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.core.user.port.out.ReadUserPort
import com.info.maeumgagym.security.authentication.provider.UserModelAuthenticationFactory
import com.info.maeumgagym.security.authentication.vo.UserModelAuthentication
import org.springframework.stereotype.Component

@Component
class UserModelAuthenticationFactoryImpl(
    private val readUserPort: ReadUserPort
) : UserModelAuthenticationFactory {

    override fun createFilledAuthentication(username: String): UserModelAuthentication =
        UserModelAuthentication(
            userSubject = username,
            user = readUserPort.readByOAuthId(username)
                ?: throw CriticalException("Cannot Find User By username : $username")
        )


    override fun createEmptyAuthentication(username: String): UserModelAuthentication =
        UserModelAuthentication(
            userSubject = username,
            user = null
        )
}
