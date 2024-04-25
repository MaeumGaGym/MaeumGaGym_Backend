package com.info.maeumgagym.core.auth.port.`in`

interface DuplicatedNicknameCheckUseCase {

    fun existByNickname(nickname: String): Boolean
}
