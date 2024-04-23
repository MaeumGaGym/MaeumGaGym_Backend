package com.info.maeumgagym.core.daily.service

import com.info.maeumgagym.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.core.daily.dto.response.DailyListResponse
import com.info.maeumgagym.core.daily.dto.response.DailyResponse
import com.info.maeumgagym.core.daily.port.`in`.ReadDailyUseCase
import com.info.maeumgagym.core.daily.port.out.ReadDailyPort

@ReadOnlyUseCase
internal class ReadDailyService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readDailyPort: ReadDailyPort
) : ReadDailyUseCase {

    private companion object {
        const val URL_FORMAT = "%s/%s/daily_exercise_complete/%s/%s/%s"
    }

    override fun readDailies(page: Int, size: Int): DailyListResponse {
        val user = readCurrentUserPort.readCurrentUser()

        val dailies = readDailyPort.readAllByUploader(user, page, size)
            .takeIf {
                it.isNotEmpty()
            }?.map {
                DailyResponse(
                    title = it.title,
                    createAt = it.date.atTime(it.time),
                    url = URL_FORMAT.format(
                        System.getenv("MINIO_END_POINT"),
                        System.getenv("MINIO_BUCKET_NAME"),
                        user.oauthId,
                        it.date,
                        it.title
                    )
                )
            } ?: throw BusinessLogicException(204, "There's No Dailies Left.")

        return DailyListResponse(dailies)
    }
}
