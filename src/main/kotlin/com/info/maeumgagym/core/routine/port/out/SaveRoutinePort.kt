package com.info.maeumgagym.core.routine.port.out

import com.info.maeumgagym.core.routine.model.Routine

interface SaveRoutinePort {
    fun save(routine: Routine): Routine
}
