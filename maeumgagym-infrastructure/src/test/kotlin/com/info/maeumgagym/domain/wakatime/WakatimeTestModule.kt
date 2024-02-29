package com.info.maeumgagym.domain.wakatime

import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.wakatime.entity.WakaTimeJpaEntity
import com.info.maeumgagym.domain.wakatime.repository.WakaTimeRepository
import com.info.maeumgagym.error.TestException
import java.time.LocalDate

internal object WakatimeTestModule {

    private const val WAKATIME_SECONDS = 30L

    fun createTodayWakatime(user: UserJpaEntity): WakaTimeJpaEntity =
        WakaTimeJpaEntity(
            user = user,
            waka = WAKATIME_SECONDS,
            date = LocalDate.now()
        )

    fun createYesterdayWakatime(user: UserJpaEntity): WakaTimeJpaEntity =
        WakaTimeJpaEntity(
            user = user,
            waka = WAKATIME_SECONDS,
            date = LocalDate.now().minusDays(1)
        )

    fun WakaTimeJpaEntity.saveInRepository(wakaTimeRepository: WakaTimeRepository): WakaTimeJpaEntity =
        wakaTimeRepository.save(this)

    fun UserJpaEntity.setWakaStartedAtToBefore10Seconds(): UserJpaEntity =
        UserJpaEntity(
            nickname = nickname,
            oauthId = oauthId,
            roles = roles,
            profileImage = profileImage,
            wakaStartedAt = wakaStartedAt?.minusSeconds(10) ?: throw RuntimeException(),
            id = id,
            physicalInfo = null
        )

    fun isSimilarWithAllowableErrorSize(a: Long, b: Long, allowableErrorSize: Long) {
        if (a - allowableErrorSize > b || a + allowableErrorSize < b) {
            throw TestException.ERROR_TOO_BIG
        }
    }
}
