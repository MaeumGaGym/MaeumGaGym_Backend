package com.info.maeumgagym.core.routine.service

import com.info.maeumgagym.common.annotation.responsibility.UseCase
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.routine.model.ExerciseInfoHistoryModel.Companion.toHistory
import com.info.maeumgagym.core.routine.model.Routine
import com.info.maeumgagym.core.routine.model.RoutineHistory
import com.info.maeumgagym.core.routine.port.`in`.CompleteRoutineUseCase
import com.info.maeumgagym.core.routine.port.out.ExistsRoutineHistoryPort
import com.info.maeumgagym.core.routine.port.out.ReadRoutinePort
import com.info.maeumgagym.core.routine.port.out.SaveRoutineHistoryPort
import com.info.maeumgagym.core.user.model.User
import java.time.LocalDate

@UseCase
class CompleteRoutineService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readRoutinePort: ReadRoutinePort,
    private val existsRoutineHistoryPort: ExistsRoutineHistoryPort,
    private val saveRoutineHistoryPort: SaveRoutineHistoryPort
) : CompleteRoutineUseCase {

    override fun completeFromId(id: Long) {
        val user = readCurrentUserPort.readCurrentUser()

        val routine = readRoutinePort.readById(id) ?: throw BusinessLogicException.ROUTINE_NOT_FOUND

        routine.checkAccessibleToRoutine(user)

        checkIsRoutineOfToday(routine)

        routine.checkNotCompletedAtToday()

        saveRoutineHistoryPort.save(
            RoutineHistory(
                id = null,
                originId = routine.id!!,
                date = LocalDate.now(),
                exerciseInfoHistoryList = routine.exerciseInfoModelList.map { it.toHistory() },
                userId = user.id!!,
                routineName = routine.routineName
            )
        )
    }

    private fun Routine.checkAccessibleToRoutine(user: User) {
        if (userId != user.id /* && !isShared */) { // 아직 기능의 구체적인 다른 구현이 없어 isShared 확인은 보류
            throw SecurityException.PERMISSION_DENIED
        }
    }

    private fun checkIsRoutineOfToday(routine: Routine) {
        if (!routine.dayOfWeeks!!.contains(LocalDate.now().dayOfWeek)) {
            throw BusinessLogicException(400, "It's Not Today's Routine")
        }
    }

    private fun Routine.checkNotCompletedAtToday() {
        if (existsRoutineHistoryPort.existByOriginIdToday(id!!)) {
            throw BusinessLogicException(400, "Already Completed Routine")
        }
    }
}
