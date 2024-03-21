package com.info.maeumgagym.auth

import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.CriticalException
import com.info.maeumgagym.security.jwt.AuthenticationProvider
import com.info.maeumgagym.security.jwt.JwtFilter
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.ReadUserPort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
internal class ReadCurrentUserAdapter(
    private val readUserPort: ReadUserPort,
    private val authenticationProvider: AuthenticationProvider
) : ReadCurrentUserPort {

    override fun readCurrentUser(): User {
        // User를 찾기 위한 정보가 담겨 있는 Authentication 로드
        val authentication = SecurityContextHolder.getContext().authentication

        JwtFilter.run {
            // Lazy Loading으로 Nullable인 User를 확인
            if (this.authenticatedUser == null) {
                // null인 경우 User를 Load 및 입력
                this.authenticatedUser = ThreadLocal.withInitial {
                    readUserPort.readByOAuthId(authentication.principal as String)
                    // authDetails에 담긴 username = oauthId는 로직상 무조건 유저가 존재해야하므로 CriticalException throw
                        ?: throw CriticalException(401, "User not found in Authentication data At ReadCurrentUserPort")
                }
            }
        }

        // Loaded User를 SecurityContext Authentication에도 반영
        SecurityContextHolder.getContext().authentication =
            authenticationProvider.getAuthentication(
                JwtFilter.authenticatedUser?.get()?.oauthId
                    // User는 위에서 설정해주었으므로 비어있을 수 없음
                    ?: throw CriticalException(500, "User loaded but It's NULL.")
            )

        // User 반환
        return JwtFilter.authenticatedUser!!.get()
    }
}
