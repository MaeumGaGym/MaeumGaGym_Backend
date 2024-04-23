package com.info.maeumgagym.core.routine.service

import com.info.maeumgagym.common.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.core.routine.model.ExerciseInfoHistoryModel.Companion.toHistory
import com.info.maeumgagym.core.routine.model.RoutineHistory
import com.info.maeumgagym.core.routine.port.`in`.CompleteTodayRoutineUseCase
import com.info.maeumgagym.core.routine.port.out.ExistsRoutineHistoryPort
import com.info.maeumgagym.core.routine.port.out.ReadRoutinePort
import com.info.maeumgagym.core.routine.port.out.SaveRoutineHistoryPort
import java.time.LocalDate

@UseCase
class CompleteTodayRoutineService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readRoutinePort: ReadRoutinePort,
    private val saveRoutineHistoryPort: SaveRoutineHistoryPort,
    private val existsRoutineHistoryPort: ExistsRoutineHistoryPort
) : CompleteTodayRoutineUseCase {
    override fun complete() {
        val user = readCurrentUserPort.readCurrentUser()

        val now = LocalDate.now()

        val routine = readRoutinePort.readByUserIdAndDayOfWeek(user.id!!, now.dayOfWeek)
            ?: throw BusinessLogicException.ROUTINE_NOT_FOUND

        if (existsRoutineHistoryPort.exsitsByUserIdAndDate(user.id, now)) {
            throw BusinessLogicException(409, "Already Completed Routine")
        } else {
            saveRoutineHistoryPort.save(
                RoutineHistory(
                    id = null,
                    date = now,
                    exerciseInfoHistoryList = routine.exerciseInfoModelList.map { it.toHistory() },
                    userId = user.id,
                    routineName = routine.routineName
                )
            )
        }
    }
}
