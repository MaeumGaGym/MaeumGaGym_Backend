package com.info.maeumgagym.auth.service

import com.info.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.auth.port.`in`.DuplicatedNicknameCheckUseCase
import com.info.maeumgagym.user.port.out.ExistUserPort

@ReadOnlyUseCase
internal class DuplicatedCheckService(
    private val existUserPort: ExistUserPort
) : DuplicatedNicknameCheckUseCase {

    override fun existByNickname(nickname: String): Boolean =
        existUserPort.existByNicknameOnWithdrawalSafe(nickname)
}
