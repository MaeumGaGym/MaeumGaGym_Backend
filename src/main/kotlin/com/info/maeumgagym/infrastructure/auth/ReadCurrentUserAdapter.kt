package com.info.maeumgagym.infrastructure.auth

import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.user.model.User
import com.info.maeumgagym.infrastructure.security.authentication.AuthenticationProvider
import com.info.maeumgagym.infrastructure.security.authentication.vo.UserModelAuthentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
internal class ReadCurrentUserAdapter(
    private val authenticationProvider: AuthenticationProvider
) : ReadCurrentUserPort {

    override fun readCurrentUser(): User {
        // User를 찾기 위한 정보가 담겨 있는 Authentication 로드
        var authentication = SecurityContextHolder.getContext().authentication as UserModelAuthentication

        // Lazy Loading이 가능하여 Nullable인 User가 null이거나, 유효하지 경우 => 유저가 이미 로딩되어 있지 않은 경우
        if (authentication.user == null ||
            authentication.user!!.oauthId != authentication.principal
        ) {
            // User를 Load 및 SecurityContext에 삽입
            authentication = authenticationProvider.getAuthentication(
                authentication.principal as String
            ) as UserModelAuthentication
        }

        // User 반환
        return authentication.user!!
    }
}
