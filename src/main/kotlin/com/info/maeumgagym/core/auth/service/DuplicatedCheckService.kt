package com.info.maeumgagym.core.auth.service

import com.info.maeumgagym.common.annotation.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.`in`.DuplicatedNicknameCheckUseCase
import com.info.maeumgagym.core.user.port.out.ExistUserPort

@ReadOnlyUseCase
internal class DuplicatedCheckService(
    private val existUserPort: ExistUserPort
) : DuplicatedNicknameCheckUseCase {

    override fun existByNickname(nickname: String): Boolean =
        existUserPort.existByNicknameOnWithdrawalSafe(nickname)
}
