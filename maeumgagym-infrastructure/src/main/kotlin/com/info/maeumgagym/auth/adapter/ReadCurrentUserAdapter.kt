package com.info.maeumgagym.auth.adapter

import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.global.exception.UnAuthorizedException
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.FindUserByUUIDPort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class ReadCurrentUserAdapter(
    private val findUserByUUIDPort: FindUserByUUIDPort
) : ReadCurrentUserPort {

    override fun readCurrentUser(): User {
        val userId = SecurityContextHolder.getContext().authentication.name
        return findUserByUUIDPort.findUserById(UUID.fromString(userId)) ?: throw UnAuthorizedException
    }
}
