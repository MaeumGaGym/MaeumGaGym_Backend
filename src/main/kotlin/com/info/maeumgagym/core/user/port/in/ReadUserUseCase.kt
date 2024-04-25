package com.info.maeumgagym.core.user.port.`in`

interface ReadUserUseCase {
    fun profileFromNickname(nickname: String): com.info.maeumgagym.core.user.dto.response.UserProfileResponse
}
