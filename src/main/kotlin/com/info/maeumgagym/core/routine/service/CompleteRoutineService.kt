package com.info.maeumgagym.core.routine.service

import com.info.maeumgagym.common.annotation.responsibility.UseCase
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.routine.model.ExerciseInfoHistoryModel.Companion.toHistory
import com.info.maeumgagym.core.routine.model.RoutineHistory
import com.info.maeumgagym.core.routine.port.`in`.CompleteRoutineUseCase
import com.info.maeumgagym.core.routine.port.out.ReadRoutineHistoryPort
import com.info.maeumgagym.core.routine.port.out.ReadRoutinePort
import com.info.maeumgagym.core.routine.port.out.SaveRoutineHistoryPort
import java.time.LocalDate

@UseCase
class CompleteRoutineService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readRoutinePort: ReadRoutinePort,
    private val readRoutineHistoryPort: ReadRoutineHistoryPort,
    private val saveRoutineHistoryPort: SaveRoutineHistoryPort
) : CompleteRoutineUseCase {

    override fun completeFromId(id: Long) {
        val user = readCurrentUserPort.readCurrentUser()

        val routine = readRoutinePort.readById(id)
            ?: throw BusinessLogicException.ROUTINE_NOT_FOUND

        val now = LocalDate.now()

        if (!routine.dayOfWeeks!!.contains(now.dayOfWeek)) {
            throw BusinessLogicException(400, "It's Not Today's Routine")
        }

        val completeRoutinesAtToday =
            readRoutineHistoryPort.readByUserIdAndDateBetweenOrderByDate(user.id!!, now, now)

        if (completeRoutinesAtToday.map { it.originId }.contains(routine.id!!)) {
            throw BusinessLogicException(400, "Already Completed Routine")
        }

        saveRoutineHistoryPort.save(
            RoutineHistory(
                id = null,
                originId = routine.id,
                date = now,
                exerciseInfoHistoryList = routine.exerciseInfoModelList.map { it.toHistory() },
                userId = user.id,
                routineName = routine.routineName
            )
        )
    }
}
