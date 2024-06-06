package com.info.maeumgagym.core.routine.port.`in`

import com.info.maeumgagym.core.routine.dto.request.UpdateRoutineRequest

interface UpdateRoutineUseCase {
    fun updateRoutine(req: UpdateRoutineRequest, routineId: Long)
}
