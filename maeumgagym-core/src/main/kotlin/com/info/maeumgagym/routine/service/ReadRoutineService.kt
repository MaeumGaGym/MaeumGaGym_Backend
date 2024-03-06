package com.info.maeumgagym.routine.service

import com.info.common.UseCase
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.routine.dto.response.RoutineDetailResponse
import com.info.maeumgagym.routine.port.`in`.ReadRoutineUseCase
import com.info.maeumgagym.routine.port.out.ReadRoutinePort

@UseCase
internal class ReadRoutineService(
    private val readRoutinePort: ReadRoutinePort
) : ReadRoutineUseCase {
    override fun readFromId(routineId: Long): RoutineDetailResponse {
        val routine = readRoutinePort.readById(routineId) ?: throw BusinessLogicException.ROUTINE_NOT_FOUND
        val routineResponse = routine.toResponse()
        return routineResponse.run {
            RoutineDetailResponse(
                routineName = routineName,
                routineStatusDto = routineStatus,
                exerciseInfoList = exerciseInfoList
            )
        }
    }
}
