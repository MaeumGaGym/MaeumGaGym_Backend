package com.info.maeumgagym.core.routine.port.out

import com.info.maeumgagym.core.routine.model.RoutineHistory

interface DeleteRoutineHistoryPort {

    fun delete(routineHistory: RoutineHistory)
}
