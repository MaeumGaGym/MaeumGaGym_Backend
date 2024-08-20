package com.info.maeumgagym.core.routine.service

import com.info.maeumgagym.common.annotation.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.routine.dto.response.CompletableRoutineResponse
import com.info.maeumgagym.core.routine.port.`in`.ReadRoutineUseCase
import com.info.maeumgagym.core.routine.port.out.ExistsRoutineHistoryPort
import com.info.maeumgagym.core.routine.port.out.ReadRoutinePort

@ReadOnlyUseCase
internal class ReadRoutineService(
    private val readRoutinePort: ReadRoutinePort,
    private val existsRoutineHistoryPort: ExistsRoutineHistoryPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : ReadRoutineUseCase {
    override fun readFromId(routineId: Long): CompletableRoutineResponse {
        val routine = readRoutinePort.readById(routineId)
            ?: throw BusinessLogicException.ROUTINE_NOT_FOUND

        if (routine.userId != readCurrentUserPort.readCurrentUser().id) {
            throw SecurityException.PERMISSION_DENIED
        }

        return routine.toResponse(
            isCompleted = existsRoutineHistoryPort.existByOriginIdToday(routine.id!!)
        )
    }
}
