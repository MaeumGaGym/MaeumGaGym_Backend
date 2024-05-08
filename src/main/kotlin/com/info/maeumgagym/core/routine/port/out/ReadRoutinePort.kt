package com.info.maeumgagym.core.routine.port.out

import com.info.maeumgagym.core.routine.model.Routine
import java.time.DayOfWeek
import java.util.*

interface ReadRoutinePort {

    fun readById(routineId: Long): Routine?

    fun readAllByUserIdPaged(userId: UUID, index: Int): List<Routine>

    fun readByUserIdAndDayOfWeekAndIsArchivedFalse(userId: UUID, dayOfWeek: DayOfWeek): List<Routine>
}
