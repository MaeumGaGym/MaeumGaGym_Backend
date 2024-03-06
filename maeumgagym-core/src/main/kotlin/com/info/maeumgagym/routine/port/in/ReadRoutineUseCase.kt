package com.info.maeumgagym.routine.port.`in`

import com.info.maeumgagym.routine.dto.response.RoutineResponse

interface ReadRoutineUseCase {
    fun readFromId(routineId: Long): RoutineResponse
}
