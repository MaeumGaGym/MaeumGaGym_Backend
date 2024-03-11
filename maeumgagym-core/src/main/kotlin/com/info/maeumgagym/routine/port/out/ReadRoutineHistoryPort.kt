package com.info.maeumgagym.routine.port.out

import com.info.maeumgagym.routine.model.RoutineHistory
import java.time.LocalDate
import java.util.*

interface ReadRoutineHistoryPort {

    fun readByUserIdAndDate(userId: UUID, date: LocalDate): RoutineHistory?
}
