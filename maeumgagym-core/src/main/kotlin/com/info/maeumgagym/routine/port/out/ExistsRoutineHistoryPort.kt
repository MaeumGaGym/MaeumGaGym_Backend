package com.info.maeumgagym.routine.port.out

import java.time.LocalDate
import java.util.*

interface ExistsRoutineHistoryPort {

    fun exsitsByUserIdAndDate(userId: UUID, date: LocalDate): Boolean
}
