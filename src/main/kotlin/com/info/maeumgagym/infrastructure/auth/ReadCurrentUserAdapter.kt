package com.info.maeumgagym.infrastructure.auth

import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.user.model.User
import com.info.maeumgagym.security.authentication.provider.AuthenticationManager
import org.springframework.stereotype.Component

@Component
internal class ReadCurrentUserAdapter(
    private val authenticationManager: AuthenticationManager
) : ReadCurrentUserPort {

    override fun readCurrentUser(): User {
        // User를 찾기 위한 정보가 담겨 있는 Authentication 로드
        return authenticationManager.getAuthentication()?.getUser()
            ?: throw CriticalException("${this::class.simpleName} Called BUT Not Authorized")
    }
}
