package com.info.maeumgagym.core.routine.service

import com.info.maeumgagym.common.annotation.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.core.routine.dto.response.RoutineResponse
import com.info.maeumgagym.core.routine.port.`in`.ReadRoutineUseCase
import com.info.maeumgagym.core.routine.port.out.ReadRoutinePort

@ReadOnlyUseCase
internal class ReadRoutineService(
    private val readRoutinePort: ReadRoutinePort
) : ReadRoutineUseCase {
    override fun readFromId(routineId: Long): RoutineResponse {
        val routine = readRoutinePort.readById(routineId) ?: throw BusinessLogicException.ROUTINE_NOT_FOUND
        return routine.toResponse()
    }
}
