package com.info.maeumgagym.core.user.port.`in`

interface ReadUserProfileFromNicknameUseCase {
    fun profileFromNickname(nickname: String): com.info.maeumgagym.core.user.dto.response.UserProfileResponse
}
