package com.info.maeumgagym.core.routine.port.`in`

import com.info.maeumgagym.common.dto.LocationSubjectDto
import com.info.maeumgagym.core.routine.dto.request.CreateRoutineRequest

interface CreateRoutineUseCase {
    fun createRoutine(req: CreateRoutineRequest): LocationSubjectDto
}
