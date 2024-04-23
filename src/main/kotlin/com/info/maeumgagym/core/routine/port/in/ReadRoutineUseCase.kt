package com.info.maeumgagym.core.routine.port.`in`

import com.info.maeumgagym.core.routine.dto.response.RoutineResponse

interface ReadRoutineUseCase {
    fun readFromId(routineId: Long): com.info.maeumgagym.core.routine.dto.response.RoutineResponse
}
