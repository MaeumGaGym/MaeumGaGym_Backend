package com.info.maeumgagym.core.daily.service

import com.info.maeumgagym.common.annotation.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.core.daily.model.Daily
import com.info.maeumgagym.core.daily.port.`in`.UpdateDailyUseCase
import com.info.maeumgagym.core.daily.port.out.ReadDailyPort
import com.info.maeumgagym.core.daily.port.out.SaveDailyPort
import java.time.LocalDate

@UseCase
internal class UpdateDailyService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readDailyPort: ReadDailyPort,
    private val saveDailyPort: SaveDailyPort
) : UpdateDailyUseCase {

    override fun updateTitle(title: String, date: LocalDate) {
        val user = readCurrentUserPort.readCurrentUser()

        val daily = readDailyPort.readByUploaderAndDate(user, date)?.run {
            Daily(
                id = id,
                title = title,
                uploader = uploader,
                date = this.date,
                time = time
            )
        } ?: throw BusinessLogicException.DAILY_NOT_FOUND

        saveDailyPort.save(daily)
    }
}
