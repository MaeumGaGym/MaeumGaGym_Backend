package com.info.maeumgagym.daily.service

import com.info.common.responsibility.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.dto.LocationSubjectDto
import com.info.maeumgagym.daily.model.Daily
import com.info.maeumgagym.daily.port.`in`.CreateDailyUseCase
import com.info.maeumgagym.daily.port.out.ReadDailyPort
import com.info.maeumgagym.daily.port.out.SaveDailyPort
import java.time.LocalDateTime

@UseCase
internal class CreateDailyService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val saveDailyPort: SaveDailyPort,
    private val readDailyPort: ReadDailyPort
) : CreateDailyUseCase {

    override fun create(title: String): LocationSubjectDto {
        val user = readCurrentUserPort.readCurrentUser()

        val now = LocalDateTime.now()

        val daily = saveDailyPort.save(
            Daily(
                id = readDailyPort.readByUploaderAndDate(user, now.toLocalDate())?.id,
                title = title,
                uploader = user,
                date = now.toLocalDate(),
                time = now.toLocalTime()
            )
        )

        return LocationSubjectDto(daily.id!!)
    }
}
