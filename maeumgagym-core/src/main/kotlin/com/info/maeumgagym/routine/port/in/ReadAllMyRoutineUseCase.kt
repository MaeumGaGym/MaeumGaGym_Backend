package com.info.maeumgagym.routine.port.`in`

import com.info.maeumgagym.routine.dto.response.RoutineListResponse

interface ReadAllMyRoutineUseCase {
    fun readAllMyRoutine(): RoutineListResponse
}
