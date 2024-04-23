package com.info.maeumgagym.core.user.port.`in`

import com.info.maeumgagym.core.user.dto.response.UserProfileResponse

interface ReadUserUseCase {
    fun profileFromNickname(nickname: String): com.info.maeumgagym.core.user.dto.response.UserProfileResponse
}
