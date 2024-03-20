package com.info.maeumgagym.security.jwt.impl

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.security.jwt.AuthenticationProvider
import com.info.maeumgagym.security.principle.CustomUserDetails
import com.info.maeumgagym.user.port.out.ReadUserPort
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

/**
 * Docs는 상위 타입에 존재
 *
 * @author Daybreak312
 * @since 20-03-2024
 */
@Component
class AuthenticationProviderImpl(
    private val userDetailsService: UserDetailsService,
    private val readUserPort: ReadUserPort
) : AuthenticationProvider {

    override fun getAuthentication(subject: String): UsernamePasswordAuthenticationToken {
        // UserDetails 생성
        val authDetails = userDetailsService.loadUserByUsername(subject) as CustomUserDetails

        val user = readUserPort.readByOAuthId(subject)
            ?: throw CriticalException(500, "User Not Found In AuthenticationProvider")

        authDetails.fillUser(user)

        // Authentication발급
        return UsernamePasswordAuthenticationToken(authDetails, null, authDetails.authorities)
    }

    override fun getEmptyAuthentication(subject: String): UsernamePasswordAuthenticationToken {
        // UserDetails 생성
        val authDetails = userDetailsService.loadUserByUsername(subject) as CustomUserDetails

        return UsernamePasswordAuthenticationToken(authDetails, null, null)
    }
}
