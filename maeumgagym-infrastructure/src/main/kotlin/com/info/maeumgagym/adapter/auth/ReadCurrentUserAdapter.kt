package com.info.maeumgagym.adapter.auth

import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.global.security.principle.CustomUserDetails
import com.info.maeumgagym.user.model.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class ReadCurrentUserAdapter : ReadCurrentUserPort {

    override fun readCurrentUser(): User =
        // jwt filter에서 집어 넣은 user를 context holder에서 꺼내오기
        (SecurityContextHolder.getContext().authentication.principal as CustomUserDetails).user
}
