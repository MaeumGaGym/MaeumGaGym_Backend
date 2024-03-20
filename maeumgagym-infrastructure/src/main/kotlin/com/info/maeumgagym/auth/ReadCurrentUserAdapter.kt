package com.info.maeumgagym.auth

import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.security.principle.CustomUserDetails
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.ReadUserPort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
internal class ReadCurrentUserAdapter(
    private val readUserPort: ReadUserPort
) : ReadCurrentUserPort {

    override fun readCurrentUser(): User {

        // jwt filter에서 생성한 authDetail를 context holder에서 불러옴
        val authDetails = SecurityContextHolder.getContext().authentication.principal as CustomUserDetails

        // Lazy Loading으로 Nullable인 User를 확인하고, null인 경우 User를 Load 및 입력
        if (authDetails.getUser() == null) {
            authDetails.fillUser(
                readUserPort.readByOAuthId(authDetails.username)
                // authDetails에 담긴 username = oauthId는 로직상 무조건 유저가 존재해야하므로 AuthenticationException throw
                    ?: throw AuthenticationException(401, "User Not Found In ReadCurrentUserPort")
            )
        }

        // 반환
        return authDetails.getUser()!!
    }
}
