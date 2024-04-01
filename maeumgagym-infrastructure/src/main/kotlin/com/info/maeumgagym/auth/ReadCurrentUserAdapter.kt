package com.info.maeumgagym.auth

import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.security.jwt.AuthenticationProvider
import com.info.maeumgagym.security.jwt.JwtFilter
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.ReadUserPort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
internal class ReadCurrentUserAdapter(
    private val authenticationProvider: AuthenticationProvider
) : ReadCurrentUserPort {

    override fun readCurrentUser(): User {
        // User를 찾기 위한 정보가 담겨 있는 Authentication 로드
        val authentication = SecurityContextHolder.getContext().authentication

        JwtFilter.run {
            // Lazy Loading으로 Nullable인 User를 확인
            if (this.authenticatedUser?.get() == null ||
                this.authenticatedUser?.get()?.oauthId != authentication!!.principal
            ) {
                // null인 경우 User를 Load 및 SecurityContext, authenticatedUser에 입력
                SecurityContextHolder.getContext().authentication =
                    authenticationProvider.getAuthentication(
                        authentication.principal as String
                    )
            }
        }

        // User 반환
        return JwtFilter.authenticatedUser!!.get()
    }
}
