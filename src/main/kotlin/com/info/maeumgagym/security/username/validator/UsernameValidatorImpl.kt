package com.info.maeumgagym.security.username.validator

import com.info.maeumgagym.core.user.port.out.ExistUserPort
import org.springframework.stereotype.Component

@Component
class UsernameValidatorImpl(
    private val existUserPort: ExistUserPort
) : UsernameValidator {

    override fun invoke(username: String): Boolean =
        existUserPort.existsByOAuthId(username)
}
