package com.info.maeumgagym.security.jwt.impl

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.security.jwt.AuthenticationProvider
import com.info.maeumgagym.security.jwt.JwtFilter
import com.info.maeumgagym.user.port.out.ReadUserPort
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

/**
 * Docs는 상위 타입에 존재
 *
 * @author Daybreak312
 * @since 20-03-2024
 */
@Component
class AuthenticationProviderImpl(
    private val readUserPort: ReadUserPort
) : AuthenticationProvider {

    override fun getAuthentication(subject: String): UsernamePasswordAuthenticationToken {
        // User가 필요한 경우 불러와 전역적으로 저장
        JwtFilter.authenticatedUser = ThreadLocal.withInitial {
            readUserPort.readByOAuthId(subject)
                ?: throw CriticalException(500, "User Not Found In AuthenticationProvider")
        }

        // Authentication에 subject를 넣어 반환
        return UsernamePasswordAuthenticationToken(
            subject, null,
            JwtFilter.authenticatedUser?.get()?.roles?.map {
                SimpleGrantedAuthority(it.name)
            })
    }

    override fun getEmptyAuthentication(subject: String): UsernamePasswordAuthenticationToken {
        // Authentication에 subject를 넣어 반환
        return UsernamePasswordAuthenticationToken(subject, null, null)
    }
}
