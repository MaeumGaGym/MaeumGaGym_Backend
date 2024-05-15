package com.info.maeumgagym.security.authentication.provider.impl

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.security.authentication.provider.AuthenticationManager
import com.info.maeumgagym.security.authentication.provider.UserModelAuthenticationFactory
import com.info.maeumgagym.security.authentication.vo.UserModelAuthentication
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationManagerImpl(
    private val userModelAuthenticationFactory: UserModelAuthenticationFactory
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

    private fun isInvalidAuthentication(authentication: Authentication?): Boolean =
        authentication == null || authentication is AnonymousAuthenticationToken
}
