package com.info.maeumgagym.core.routine.port.out

import com.info.maeumgagym.routine.model.RoutineHistory

interface SaveRoutineHistoryPort {

    fun save(routineHistory: RoutineHistory): RoutineHistory
}
