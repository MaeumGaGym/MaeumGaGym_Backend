package com.info.maeumgagym.core.routine.port.`in`

import com.info.maeumgagym.core.routine.dto.request.UpdateRoutineRequest

interface UpdateRoutineUseCase {
    fun updateRoutine(req: com.info.maeumgagym.core.routine.dto.request.UpdateRoutineRequest, routineId: Long)
}
