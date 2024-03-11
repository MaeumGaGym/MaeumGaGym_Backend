package com.info.maeumgagym.routine.port.out

import com.info.maeumgagym.routine.model.RoutineHistory

interface SaveRoutineHistoryPort {

    fun save(routineHistory: RoutineHistory): RoutineHistory
}
