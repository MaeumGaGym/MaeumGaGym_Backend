package com.info.maeumgagym.scheduler

import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.wakatime.model.WakaTime
import com.info.maeumgagym.wakatime.port.out.ReadWakaTimeFromUserAndDatePort
import com.info.maeumgagym.wakatime.port.out.SaveWakaTimePort
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime


@Transactional
@Service
class WakaTimeScheduler(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val saveWakaTimePort: SaveWakaTimePort,
    private val readWakaTimeFromUserAndDatePort: ReadWakaTimeFromUserAndDatePort
) {

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    fun restartAllWakaTime() {
        val nowDate = LocalDate.now()
        val nowDateTime = LocalDateTime.now()
        var seconds: Long

        userRepository.findAllByWakaStartedAtNotNullInNative().forEach { user ->
            seconds = Duration.between(userMapper.toDomain(user).wakaStartedAt, nowDateTime).seconds

            saveWakaTimePort.save(
                readWakaTimeFromUserAndDatePort.findByUserAndDate(userMapper.toDomain(user), nowDate)
                    ?.let {
                        // 있으면 waka += seconds
                        WakaTime(
                            user = it.user,
                            waka = it.waka + seconds,
                            date = it.date,
                            isNew = it.isNew
                        )
                    } ?: WakaTime(
                    // 없으면 waka = seconds
                    user = userMapper.toDomain(user),
                    waka = seconds,
                    date = nowDate
                ))

            userRepository.save(
                user.run {
                    UserJpaEntity(
                        nickname = nickname,
                        oauthId = oauthId,
                        roles = roles,
                        profileImage = profileImage,
                        wakaStartedAt = nowDateTime,
                        isDelete = isDeleted,
                        id = id
                    )
                }
            )
        }
    }
}
