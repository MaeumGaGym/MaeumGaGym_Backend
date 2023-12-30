package com.info.maeumgagym.routine.port.out

import com.info.maeumgagym.routine.model.Routine

interface ReadRoutineByIdPort {
    fun readRoutineById(routineId: Long): Routine?
}
