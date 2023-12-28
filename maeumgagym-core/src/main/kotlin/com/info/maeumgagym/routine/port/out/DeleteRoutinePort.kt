package com.info.maeumgagym.routine.port.out

import com.info.maeumgagym.routine.model.Routine
import java.util.UUID

interface DeleteRoutinePort {
    fun deleteRoutine(routine: Routine)
}
