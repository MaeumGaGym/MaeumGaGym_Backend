package com.info.maeumgagym.daily.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.exception.PermissionDeniedException
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.daily.exception.DailyNotFoundException
import com.info.maeumgagym.daily.port.`in`.DeleteDailyUseCase
import com.info.maeumgagym.daily.port.out.DeleteDailyPort
import com.info.maeumgagym.daily.port.out.DeleteImageObjectPort
import com.info.maeumgagym.daily.port.out.ReadDailyPort
import java.time.LocalDate

@UseCase
internal class DeleteDailyService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val deleteImageObjectPort: DeleteImageObjectPort,
    private val readDailyPort: ReadDailyPort,
    private val deleteDailyPort: DeleteDailyPort
) : DeleteDailyUseCase {

    private companion object {
        const val DEFAULT_FOLDER = "daily_exercise_complete"
    }

    override fun delete(date: LocalDate) {
        val user = readCurrentUserPort.readCurrentUser()

        val daily = readDailyPort.readByUploaderAndDate(user, LocalDate.now()) ?: throw DailyNotFoundException

        if (daily.uploader != user) throw PermissionDeniedException

        deleteImageObjectPort.deleteObjects("$DEFAULT_FOLDER/${user.oauthId}/$date")

        deleteDailyPort.delete(daily)
    }
}
