package com.info.maeumgagym.security.authentication.provider.impl

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.security.authentication.provider.AuthenticationManager
import com.info.maeumgagym.security.authentication.provider.UserModelAuthenticationFactory
import com.info.maeumgagym.security.authentication.vo.UserModelAuthentication
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationManagerImpl(
    private val userModelAuthenticationFactory: UserModelAuthenticationFactory
) : AuthenticationManager {

    override fun getAuthentication(): UserModelAuthentication? {
        val context = SecurityContextHolder.getContext()

        if (isInvalidAuthentication(context.authentication)) {
            return null
        }

        try {
            if (!(context.authentication as UserModelAuthentication).isUserLoaded()) {
                setAuthentication((context.authentication as UserModelAuthentication).name)
            }

            return context.authentication as UserModelAuthentication
        } catch (e: TypeCastException) {
            throw CriticalException("Got Unknown Authentication")
        }
    }

    override fun setAuthentication(username: String) {
        SecurityContextHolder.getContext().authentication =
            userModelAuthenticationFactory.createFilledAuthentication(username)
    }

    override fun setUserNotLoadedAuthentication(username: String) {
        SecurityContextHolder.getContext().authentication =
            userModelAuthenticationFactory.createEmptyAuthentication(username)
    }

    override fun clear() {
        SecurityContextHolder.clearContext()
    }

    private fun isInvalidAuthentication(authentication: Authentication?): Boolean =
        authentication == null || authentication !is UserModelAuthentication
}
