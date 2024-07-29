package com.info.maeumgagym.core.routine.port.`in`

import com.info.maeumgagym.core.routine.dto.response.RoutineResponse

interface ReadMyRoutineFromIdUseCase {

    fun readFromId(routineId: Long): RoutineResponse
}
