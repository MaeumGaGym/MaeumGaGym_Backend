package com.info.maeumgagym.routine.port.out

import com.info.maeumgagym.routine.model.Routine

interface SaveRoutinePort {
    fun save(routine: Routine): Routine
}
