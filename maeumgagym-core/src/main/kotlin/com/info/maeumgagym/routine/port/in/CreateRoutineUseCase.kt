package com.info.maeumgagym.routine.port.`in`

import com.info.maeumgagym.routine.dto.request.CreateRoutineRequest

interface CreateRoutineUseCase {
    fun createRoutine(req: CreateRoutineRequest)
}
