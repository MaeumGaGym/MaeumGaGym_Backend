package com.info.maeumgagym.security.authentication.impl

import com.info.maeumgagym.security.authentication.UsernamePasswordAuthenticationTokenProvider
import com.info.maeumgagym.security.jwt.JwtFilter
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
class UsernamePasswordAuthenticationTokenProviderImpl : UsernamePasswordAuthenticationTokenProvider {

    override fun getAuthentication(subject: String): UsernamePasswordAuthenticationToken {
        // Authentication에 subject를 넣어 반환
        return UsernamePasswordAuthenticationToken(
            subject,
            null,
            JwtFilter.authenticatedUser.get()?.roles?.map {
                SimpleGrantedAuthority(it.name)
            }
        )
    }

    override fun getEmptyAuthentication(subject: String): UsernamePasswordAuthenticationToken {
        // Authentication에 subject를 넣어 반환
        return UsernamePasswordAuthenticationToken(subject, null, null)
    }
}
