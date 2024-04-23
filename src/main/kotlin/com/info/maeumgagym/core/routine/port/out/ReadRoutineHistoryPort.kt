package com.info.maeumgagym.core.routine.port.out

import com.info.maeumgagym.routine.model.RoutineHistory
import java.time.LocalDate
import java.util.*

interface ReadRoutineHistoryPort {

    fun readByUserIdAndDate(userId: UUID, date: LocalDate): RoutineHistory?

    fun readByUserIdAndDateBetweenOrderByDate(
        userId: UUID,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<RoutineHistory>
}
