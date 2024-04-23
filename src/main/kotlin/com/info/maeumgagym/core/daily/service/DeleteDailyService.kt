package com.info.maeumgagym.core.daily.service

import com.info.common.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.core.common.exception.SecurityException
import com.info.maeumgagym.core.daily.port.`in`.DeleteDailyUseCase
import com.info.maeumgagym.core.daily.port.out.DeleteDailyPort
import com.info.maeumgagym.core.daily.port.out.DeleteImageObjectPort
import com.info.maeumgagym.core.daily.port.out.ReadDailyPort
import java.time.LocalDate

@UseCase
internal class DeleteDailyService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val deleteImageObjectPort: DeleteImageObjectPort,
    private val readDailyPort: ReadDailyPort,
    private val deleteDailyPort: DeleteDailyPort
) : DeleteDailyUseCase {

    override fun delete(date: LocalDate) {
        val user = readCurrentUserPort.readCurrentUser()

        val daily = readDailyPort.readByUploaderAndDate(user, date)
            ?: throw BusinessLogicException.DAILY_NOT_FOUND

        if (daily.uploader != user) throw SecurityException.PERMISSION_DENIED

        deleteImageObjectPort.deleteObjects(user.oauthId, date, daily.title)

        deleteDailyPort.delete(daily)
    }
}
