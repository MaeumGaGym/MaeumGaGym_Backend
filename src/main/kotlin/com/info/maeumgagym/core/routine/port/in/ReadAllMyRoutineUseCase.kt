package com.info.maeumgagym.core.routine.port.`in`

import com.info.maeumgagym.core.routine.dto.response.RoutineListResponse

interface ReadAllMyRoutineUseCase {
    fun readAllMyRoutine(index: Int): com.info.maeumgagym.core.routine.dto.response.RoutineListResponse
}
