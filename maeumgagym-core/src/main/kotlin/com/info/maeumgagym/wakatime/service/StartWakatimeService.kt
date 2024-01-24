package com.info.maeumgagym.wakatime.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.SaveUserPort
import com.info.maeumgagym.wakatime.exception.AlreadyWakaStartedException
import com.info.maeumgagym.wakatime.port.`in`.StartWakatimeUseCase
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@UseCase
@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = [Exception::class])
internal class StartWakatimeService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val saveUserPort: SaveUserPort
) : StartWakatimeUseCase {

    override fun startWakatime() {
        // 토큰으로 user불러오기
        val user = readCurrentUserPort.readCurrentUser()

        // 이미 와카타임을 시작했는지 확인, 이미 시작했다면 -> Exception
        user.wakaStartedAt?.let { throw AlreadyWakaStartedException }

        // 와카타임 시작
        user.run {
            saveUserPort.save(
                User(
                    id = id,
                    nickname = nickname,
                    roles = roles,
                    oauthId = oauthId,
                    profileImage = profileImage,
                    wakaStartedAt = LocalDateTime.now(),
                    isDeletedAt = isDeletedAt
                )
            )
        }
    }
}
