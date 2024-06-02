package com.info.maeumgagym.core.routine.service

import com.info.maeumgagym.common.annotation.responsibility.UseCase
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.routine.port.`in`.IncompleteRoutineUseCase
import com.info.maeumgagym.core.routine.port.out.DeleteRoutineHistoryPort
import com.info.maeumgagym.core.routine.port.out.ReadRoutineHistoryPort
import com.info.maeumgagym.core.routine.port.out.ReadRoutinePort
import java.time.LocalDate

@UseCase
class IncompleteRoutineService(
    private val readRoutinePort: ReadRoutinePort,
    private val readRoutineHistoryPort: ReadRoutineHistoryPort,
    private val deleteRoutineHistoryPort: DeleteRoutineHistoryPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : IncompleteRoutineUseCase {

    override fun incompleteRoutine(originRoutineId: Long, date: LocalDate) {
        val user = readCurrentUserPort.readCurrentUser()

        val history = readRoutineHistoryPort.readByOriginIdAndDate(originRoutineId, LocalDate.now())
            ?: throw BusinessLogicException.ROUTINE_HISTORY_NOT_FOUND

        if (history.userId != user.id) {
            throw SecurityException.PERMISSION_DENIED
        }

        deleteRoutineHistoryPort.delete(history)
    }
}
