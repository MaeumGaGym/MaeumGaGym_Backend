package com.info.maeumgagym.routine.port.`in`

import com.info.maeumgagym.routine.dto.request.UpdateRoutineRequest

interface UpdateRoutineUseCase {
    fun updateRoutine(req: UpdateRoutineRequest, routineId: Long)
}
