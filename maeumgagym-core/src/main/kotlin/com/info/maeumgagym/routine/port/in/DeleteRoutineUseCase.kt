package com.info.maeumgagym.routine.port.`in`

import java.util.UUID

interface DeleteRoutineUseCase {
    fun deleteRoutine(routineId: UUID)
}
