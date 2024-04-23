package com.info.maeumgagym.core.wakatime.service

import com.info.common.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.wakatime.port.`in`.StartWakatimeUseCase
import java.time.LocalDateTime

@UseCase
internal class StartWakatimeService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val saveUserPort: com.info.maeumgagym.core.user.port.out.SaveUserPort
) : StartWakatimeUseCase {

    override fun startWakatime() {
        // 토큰으로 user불러오기
        val user = readCurrentUserPort.readCurrentUser()

        // 이미 와카타임을 시작했는지 확인, 이미 시작했다면 -> Exception
        user.wakaStartedAt?.let { throw BusinessLogicException(409, "Waka Time Alreay Started.") }

        // 와카타임 시작
        user.run {
            saveUserPort.save(
                com.info.maeumgagym.core.user.model.User(
                    id = id,
                    nickname = nickname,
                    roles = roles,
                    oauthId = oauthId,
                    profileImage = profileImage,
                    wakaStartedAt = LocalDateTime.now(),
                    isDeletedAt = isDeletedAt,
                    physicalInfoModel = null
                )
            )
        }
    }
}
