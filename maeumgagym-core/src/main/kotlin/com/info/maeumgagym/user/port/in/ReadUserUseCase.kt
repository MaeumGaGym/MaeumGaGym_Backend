package com.info.maeumgagym.user.port.`in`

import com.info.maeumgagym.user.dto.response.UserProfileResponse

interface ReadUserUseCase {
    fun profileFromNickname(nickname: String): UserProfileResponse
}
