package com.info.maeumgagym.core.user.service

import com.info.maeumgagym.common.annotation.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.user.dto.response.UserProfileResponse
import com.info.maeumgagym.core.user.port.`in`.ReadProfileUseCase
import com.info.maeumgagym.core.wakatime.util.WakatimeUtils

@ReadOnlyUseCase
class ReadProfileService(
    private val readCurrentUserPort: ReadCurrentUserPort
) : ReadProfileUseCase {

    override fun readProfile(): UserProfileResponse {
        val user = readCurrentUserPort.readCurrentUser()

        return UserProfileResponse(
            nickname = user.nickname,
            profileImage = user.profileImage,
            level = WakatimeUtils.findLevel(user.totalWakaTime),
            totalWakatime = user.totalWakaTime
        )
    }
}
