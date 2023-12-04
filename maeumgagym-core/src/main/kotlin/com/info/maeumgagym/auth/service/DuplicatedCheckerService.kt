package com.info.maeumgagym.auth.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.`in`.DuplicatedNicknameCheckUseCase
import com.info.maeumgagym.user.port.out.ExistUserByNicknamePort

@UseCase
class DuplicatedCheckerService(
    private val existUserByNicknamePort: ExistUserByNicknamePort
): DuplicatedNicknameCheckUseCase {

    override fun existByNickname(nickname: String): Boolean = existUserByNicknamePort.existByNickname(nickname)
}
