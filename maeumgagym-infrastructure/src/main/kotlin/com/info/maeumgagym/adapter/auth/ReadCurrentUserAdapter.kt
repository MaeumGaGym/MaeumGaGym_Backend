package com.info.maeumgagym.adapter.auth

import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.global.exception.UnAuthorizedException
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.FindUserByOAuthIdPort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class ReadCurrentUserAdapter(
    private val findUserByOAuthIdPort: FindUserByOAuthIdPort
) : ReadCurrentUserPort {

    override fun readCurrentUser(): User = findUserByOAuthIdPort.findUserByOAuthId(
        // jwt filter에서 집어 넣은 user를 context holder에서 꺼내오기
        SecurityContextHolder.getContext().authentication.name
    ) ?: throw UnAuthorizedException
}
