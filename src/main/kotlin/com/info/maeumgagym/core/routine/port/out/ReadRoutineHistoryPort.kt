package com.info.maeumgagym.core.routine.port.out

import com.info.maeumgagym.core.routine.model.RoutineHistory
import java.time.LocalDate
import java.util.*

interface ReadRoutineHistoryPort {

    fun readByUserIdAndDateBetweenOrderByDate(
        userId: UUID,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<RoutineHistory>
}
