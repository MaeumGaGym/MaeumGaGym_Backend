package com.info.maeumgagym.core.auth.service

import com.info.maeumgagym.common.annotation.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.`in`.WithdrawalUserUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.auth.port.out.RevokeTokensPort
import com.info.maeumgagym.core.user.port.out.DeleteUserPort

@UseCase
internal class WithdrawalUserService(
    private val deleteUserPort: DeleteUserPort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val revokeTokensPort: RevokeTokensPort
) : WithdrawalUserUseCase {

    // 회원 탈퇴 함수
    override fun withdrawal() {
        // 토큰으로 유저 불러오기
        val user = readCurrentUserPort.readCurrentUser()

        // user soft delete
        deleteUserPort.deleteById(user.id!!)

        // 토큰 만료시키기
        revokeTokensPort.revoke()
    }
}
