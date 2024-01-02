package com.info.maeumgagym.routine.port.`in`

import com.info.maeumgagym.routine.dto.request.UpdateRoutineRequest
import com.info.maeumgagym.routine.model.Routine

interface UpdateRoutineUseCase {
    fun updateRoutine(updateRoutineRequest: UpdateRoutineRequest, routineId: Long): Routine
}
