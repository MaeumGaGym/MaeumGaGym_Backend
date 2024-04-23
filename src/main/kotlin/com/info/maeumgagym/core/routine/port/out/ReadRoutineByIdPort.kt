package com.info.maeumgagym.core.routine.port.out

import com.info.maeumgagym.core.routine.model.Routine

interface ReadRoutineByIdPort {
    fun readRoutineById(routineId: Long): com.info.maeumgagym.core.routine.model.Routine?
}
