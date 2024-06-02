package com.info.maeumgagym.core.routine.port.out

import java.time.LocalDate
import java.util.*

interface ExistsRoutineHistoryPort {

    fun existsByUserIdAndDate(userId: UUID, date: LocalDate): Boolean

    fun existByOriginIdToday(originId: Long): Boolean
}
