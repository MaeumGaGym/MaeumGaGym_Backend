package com.info.maeumgagym.routine.port.`in`

import com.info.maeumgagym.routine.dto.response.RoutineDetailResponse

interface ReadRoutineUseCase {
    fun readFromId(routineId: Long): RoutineDetailResponse
}
