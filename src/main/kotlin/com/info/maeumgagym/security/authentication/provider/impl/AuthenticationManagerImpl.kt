package com.info.maeumgagym.security.authentication.provider.impl

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.core.user.port.out.ReadUserPort
import com.info.maeumgagym.security.authentication.provider.AuthenticationManager
import com.info.maeumgagym.security.authentication.vo.UserModelAuthentication
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationManagerImpl(
    private val readUserPort: ReadUserPort
) : AuthenticationManager {

    override fun getAuthentication(username: String): UserModelAuthentication {
        val context = SecurityContextHolder.getContext()

        if (isInvalidAuthentication(context.authentication)) {
            setAuthentication(username)
        }

        try {
            if (!(context.authentication as UserModelAuthentication).isUserLoaded()) {
                setAuthentication(username)
            }

            return createUserLoadedAuthentication(context.authentication as UserModelAuthentication)
        } catch (e: TypeCastException) {
            throw CriticalException("Got Unknown Authentication")
        }
    }

    private fun createUserLoadedAuthentication(authentication: UserModelAuthentication) = UserModelAuthentication(
        userSubject = authentication.name,
        user = readUserPort.readByOAuthId(authentication.name)
    )

    override fun setAuthentication(username: String) {
        SecurityContextHolder.getContext().authentication = UserModelAuthentication(
            userSubject = username,
            user = readUserPort.readByOAuthId(username)
                ?: throw CriticalException("Cannot Find User By username : $username")
        )
    }

    override fun setUserNotLoadedAuthentication(username: String) {
        SecurityContextHolder.getContext().authentication = UserModelAuthentication(
            userSubject = username,
            user = null
        )
    }

    private fun isInvalidAuthentication(authentication: Authentication?): Boolean =
        authentication == null || authentication is AnonymousAuthenticationToken
}
