package com.info.maeumgagym.domain.wakatime.module

import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.wakatime.entity.WakaTimeJpaEntity
import com.info.maeumgagym.domain.wakatime.exception.ErrorTooBigException
import com.info.maeumgagym.domain.wakatime.repository.WakaTimeRepository
import java.time.LocalDate

internal object WakatimeTestModule {

    const val WAKATIME_SECONDS = 30L

    fun createTodayWakatime(user: UserJpaEntity): WakaTimeJpaEntity =
        WakaTimeJpaEntity(
            user = user,
            waka = WAKATIME_SECONDS,
            date = LocalDate.now(),
            isNew = true
        )

    fun createYesterdayWakatime(user: UserJpaEntity): WakaTimeJpaEntity =
        WakaTimeJpaEntity(
            user = user,
            waka = WAKATIME_SECONDS,
            date = LocalDate.now().minusDays(1),
            isNew = true
        )

    fun WakaTimeJpaEntity.saveInRepository(wakaTimeRepository: WakaTimeRepository): WakaTimeJpaEntity =
        wakaTimeRepository.save(this)

    fun UserJpaEntity.setWakaStartedAtToBefore10Seconds(): UserJpaEntity =
        UserJpaEntity(
            nickname = nickname,
            oauthId = oauthId,
            roles = roles,
            profileImage = profileImage,
            wakaStartedAt = wakaStartedAt?.minusSeconds(10) ?: throw RuntimeException()
        )

    fun isSimilarWithAllowableErrorSize(a: Long, b: Long, allowableErrorSize: Long) {
        if (a - allowableErrorSize > b || a + allowableErrorSize < b)
            throw ErrorTooBigException
    }
}
