package com.info.maeumgagym.domain.auth

import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.security.principle.CustomUserDetails
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

internal object AuthTestModule {

    const val TOKEN_HEADER = "Authorization"
    const val TOKEN_PREFIX = "Bearer "

    fun UserJpaEntity.saveInContext(userMapper: UserMapper): UserJpaEntity =
        apply {
            SecurityContextHolder.getContext().authentication =
                CustomUserDetails(userMapper.toDomain(this), this.oauthId).run {
                    UsernamePasswordAuthenticationToken(this, null, this.authorities)
                }
        }

    /**
     * @return is context not null
     */
    fun clearContext(): Boolean =
        SecurityContextHolder.getContext().authentication?.let {
            SecurityContextHolder.clearContext()
            true
        } ?: false
}
