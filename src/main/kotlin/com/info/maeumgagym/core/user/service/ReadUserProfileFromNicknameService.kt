package com.info.maeumgagym.core.user.service

import com.info.maeumgagym.common.annotation.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.core.user.dto.response.UserProfileResponse
import com.info.maeumgagym.core.user.port.`in`.ReadUserProfileFromNicknameUseCase
import com.info.maeumgagym.core.user.port.out.ReadUserPort
import com.info.maeumgagym.core.wakatime.util.WakatimeUtils

@ReadOnlyUseCase
internal class ReadUserProfileFromNicknameService(
    private val readUserPort: ReadUserPort
) : ReadUserProfileFromNicknameUseCase {

    override fun profileFromNickname(nickname: String): UserProfileResponse =
        readUserPort.readByNickname(nickname)?.run {
            UserProfileResponse(
                nickname,
                profileImage,
                WakatimeUtils.findLevel(totalWakaTime),
                totalWakaTime
            )
        } ?: throw BusinessLogicException.USER_NOT_FOUND
}
