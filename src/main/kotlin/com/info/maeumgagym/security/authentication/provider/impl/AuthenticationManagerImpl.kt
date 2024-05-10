package com.info.maeumgagym.security.authentication.provider.impl

import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.core.user.port.out.ReadUserPort
import com.info.maeumgagym.security.authentication.provider.AuthenticationManager
import com.info.maeumgagym.security.authentication.vo.UserModelAuthentication
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthenticationManagerImpl(
    private val readUserPort: ReadUserPort
) : AuthenticationManager {

    override fun getAuthentication(username: String): Authentication {

        val context = SecurityContextHolder.getContext()

        if (context.authentication != null) {
            return context.authentication
        }

        setAuthentication(username)

        return context.authentication
    }

    override fun getAuthenticationOrNull(): Authentication? =
        SecurityContextHolder.getContext().authentication

    override fun getAuthenticationNotNull(username: String): Authentication =
        SecurityContextHolder.getContext().authentication
            ?: throw AuthenticationException.SECURITY_CONTEXT_NOT_INITILIZED

    override fun setAuthentication(username: String) {
        SecurityContextHolder.getContext().authentication = UserModelAuthentication(
            userSubject = username,
            user = readUserPort.readByOAuthId(username)
                ?: throw CriticalException("Cannot Find User By username : $username")
        )
    }

    override fun setAuthentication(authentication: Authentication) {
        SecurityContextHolder.getContext().authentication = authentication
    }
}
