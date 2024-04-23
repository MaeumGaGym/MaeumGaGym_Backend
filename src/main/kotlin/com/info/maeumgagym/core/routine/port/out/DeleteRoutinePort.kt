package com.info.maeumgagym.core.routine.port.out

import com.info.maeumgagym.core.routine.model.Routine

interface DeleteRoutinePort {
    fun delete(routine: com.info.maeumgagym.core.routine.model.Routine)
}
