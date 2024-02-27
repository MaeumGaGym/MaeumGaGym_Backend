package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.`in`.LogOutUseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.auth.port.out.RevokeTokensPort

@UseCase
internal class LogOutService(
    private val revokeTokensPort: RevokeTokensPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : LogOutUseCase {
    override fun logout() {
        val user = readCurrentUserPort.readCurrentUser()
        revokeTokensPort.revoke(user.oauthId)
    }
}
