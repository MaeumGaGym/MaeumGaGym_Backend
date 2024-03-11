package com.info.maeumgagym.routine.service

import com.info.common.ReadOnlyUseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.routine.dto.response.RoutineResponse
import com.info.maeumgagym.routine.port.`in`.ReadTodayRoutineUseCase
import com.info.maeumgagym.routine.port.out.ExistsRoutineHistoryPort
import com.info.maeumgagym.routine.port.out.ReadRoutinePort
import java.time.LocalDate

@ReadOnlyUseCase
class ReadTodayRoutineService(
    private val readRoutinePort: ReadRoutinePort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val existsRoutineHistoryPort: ExistsRoutineHistoryPort
) : ReadTodayRoutineUseCase {

    override fun readTodayRoutine(): RoutineResponse? {
        val userId = readCurrentUserPort.readCurrentUser().id!!

        val now = LocalDate.now()

        return readRoutinePort.readByUserIdAndDayOfWeekAndIsArchivedFalse(
            userId,
            now.dayOfWeek
        )?.run {
            toResponse(existsRoutineHistoryPort.exsitsByUserIdAndDate(userId, now))
        } ?: throw BusinessLogicException(204, "There's No Content Left")
    }
}
