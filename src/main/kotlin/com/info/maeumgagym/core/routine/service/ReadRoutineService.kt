package com.info.maeumgagym.core.routine.service

import com.info.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.core.routine.dto.response.RoutineResponse
import com.info.maeumgagym.routine.port.`in`.ReadRoutineUseCase
import com.info.maeumgagym.routine.port.out.ReadRoutinePort

@ReadOnlyUseCase
internal class ReadRoutineService(
    private val readRoutinePort: ReadRoutinePort
) : ReadRoutineUseCase {
    override fun readFromId(routineId: Long): com.info.maeumgagym.core.routine.dto.response.RoutineResponse {
        val routine = readRoutinePort.readById(routineId) ?: throw BusinessLogicException.ROUTINE_NOT_FOUND
        return routine.toResponse()
    }
}
