package com.info.maeumgagym.core.routine.service

import com.info.maeumgagym.common.annotation.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.routine.dto.response.RoutineResponse
import com.info.maeumgagym.core.routine.port.`in`.ReadMyRoutineFromIdUseCase
import com.info.maeumgagym.core.routine.port.out.ReadRoutinePort

@ReadOnlyUseCase
class ReadMyRoutineFromIdService(
    private val readRoutinePort: ReadRoutinePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : ReadMyRoutineFromIdUseCase {

    override fun readFromId(routineId: Long): RoutineResponse {
        val routine = readRoutinePort.readById(routineId)
            ?: throw BusinessLogicException.ROUTINE_NOT_FOUND

        if (routine.userId != readCurrentUserPort.readCurrentUser().id) {
            throw SecurityException.PERMISSION_DENIED
        }

        return routine.toResponse()
    }
}
