package com.info.maeumgagym.core.user.service

import com.info.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.core.user.dto.response.UserProfileResponse
import com.info.maeumgagym.core.user.port.`in`.ReadUserUseCase
import com.info.maeumgagym.core.user.port.out.ReadUserPort
import com.info.maeumgagym.wakatime.util.WakatimeUtils

@ReadOnlyUseCase
internal class ReadUserService(
    private val readUserPort: com.info.maeumgagym.core.user.port.out.ReadUserPort
) : com.info.maeumgagym.core.user.port.`in`.ReadUserUseCase {

    override fun profileFromNickname(nickname: String): com.info.maeumgagym.core.user.dto.response.UserProfileResponse =
        readUserPort.readByNickname(nickname)?.run {
            com.info.maeumgagym.core.user.dto.response.UserProfileResponse(
                nickname,
                profileImage,
                WakatimeUtils.findLevel(totalWakaTime),
                totalWakaTime
            )
        } ?: throw BusinessLogicException.USER_NOT_FOUND
}
