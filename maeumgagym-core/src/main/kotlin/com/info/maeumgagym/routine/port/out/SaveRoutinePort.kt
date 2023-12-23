package com.info.maeumgagym.routine.port.out

import com.info.maeumgagym.routine.model.Routine

interface SaveRoutinePort {
    fun saveRoutine(routine: Routine): Routine
}
