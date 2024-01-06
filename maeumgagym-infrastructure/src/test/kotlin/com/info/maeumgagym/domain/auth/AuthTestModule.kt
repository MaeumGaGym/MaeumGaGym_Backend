package com.info.maeumgagym.domain.auth

import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.global.security.principle.CustomUserDetails
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

object AuthTestModule {

    const val TOKEN_HEADER = "Authorization"
    const val TOKEN_PREFIX = "Bearer "

    fun UserJpaEntity.saveInContext(userMapper: UserMapper): UserJpaEntity =
        apply {
            SecurityContextHolder.getContext().authentication =
                CustomUserDetails(userMapper.toDomain(this)).run {
                    UsernamePasswordAuthenticationToken(this, null, this.authorities)
                }
        }

    fun clearContext(): Boolean {
        return if (SecurityContextHolder.getContext().authentication != null) {
            SecurityContextHolder.getContext().authentication = null
            true
        } else
            false
    }
}
