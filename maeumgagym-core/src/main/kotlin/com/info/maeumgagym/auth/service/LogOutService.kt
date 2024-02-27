package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.`in`.LogOutUseCase
import com.info.maeumgagym.auth.port.out.DeleteRefreshTokenPort
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort

@UseCase
internal class LogOutService(
    private val deleteRefreshTokenPort: DeleteRefreshTokenPort,
    private val readCurrentUserPort: ReadCurrentUserPort
): LogOutUseCase {
    override fun logout() {
        val user = readCurrentUserPort.readCurrentUser()
        deleteRefreshTokenPort.deleteByOAuthId(user.oauthId)
    }
}
