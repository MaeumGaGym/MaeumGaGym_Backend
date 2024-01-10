package com.info.maeumgagym.auth.port.`in`

interface DuplicatedNicknameCheckUseCase {

    fun existByNickname(nickname: String): Boolean
}
