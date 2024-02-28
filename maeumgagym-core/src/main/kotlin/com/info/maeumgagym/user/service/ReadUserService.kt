package com.info.maeumgagym.user.service

import com.info.common.ReadOnlyUseCase
import com.info.maeumgagym.user.dto.response.UserProfileResponse
import com.info.maeumgagym.user.exception.UserNotFoundException
import com.info.maeumgagym.user.port.`in`.ReadUserUseCase
import com.info.maeumgagym.user.port.out.ReadUserPort
import com.info.maeumgagym.wakatime.util.WakatimeUtils

@ReadOnlyUseCase
internal class ReadUserService(
    private val readUserPort: ReadUserPort
) : ReadUserUseCase {

    override fun profileFromNickname(nickname: String): UserProfileResponse =
        readUserPort.readByNickname(nickname)?.run {
            UserProfileResponse(
                nickname,
                profileImage,
                WakatimeUtils.findLevel(totalWakaTime),
                totalWakaTime
            )
        } ?: throw UserNotFoundException
}
