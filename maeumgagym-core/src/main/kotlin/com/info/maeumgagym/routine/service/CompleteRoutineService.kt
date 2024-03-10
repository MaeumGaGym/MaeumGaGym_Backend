package com.info.maeumgagym.routine.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.routine.model.RoutineHistory
import com.info.maeumgagym.routine.port.`in`.CompleteTodayRoutineUseCase
import com.info.maeumgagym.routine.port.out.ReadRoutineHistoryPort
import com.info.maeumgagym.routine.port.out.ReadRoutinePort
import com.info.maeumgagym.routine.port.out.SaveRoutineHistoryPort
import java.time.LocalDate

@UseCase
class CompleteRoutineService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readRoutinePort: ReadRoutinePort,
    private val readRoutineHistoryPort: ReadRoutineHistoryPort,
    private val saveRoutineHistoryPort: SaveRoutineHistoryPort
) : CompleteTodayRoutineUseCase {
    override fun complete() {
        val user = readCurrentUserPort.readCurrentUser()

        val now = LocalDate.now()

        val routine = readRoutinePort.readByUserIdAndDayOfWeek(user.id!!, now.dayOfWeek)
            ?: throw BusinessLogicException.ROUTINE_NOT_FOUND

        readRoutineHistoryPort.readByUserIdAndDate(user.id, now)?.let {
            throw BusinessLogicException(409, "Already Completed Routine")
        } ?: saveRoutineHistoryPort.save(
            RoutineHistory(
                id = null,
                date = now,
                exerciseInfoList = routine.exerciseInfoModelList,
                userId = user.id
            )
        )
    }
}
