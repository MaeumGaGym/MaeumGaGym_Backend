package com.info.maeumgagym.routine.port.out

import com.info.maeumgagym.routine.model.Routine

interface DeleteRoutinePort {
    fun delete(routine: Routine)
}
