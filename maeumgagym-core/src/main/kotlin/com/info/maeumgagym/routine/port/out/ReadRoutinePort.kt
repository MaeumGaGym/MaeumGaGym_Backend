package com.info.maeumgagym.routine.port.out

import com.info.maeumgagym.routine.model.Routine
import java.time.DayOfWeek
import java.util.*

interface ReadRoutinePort {

    fun readById(routineId: Long): Routine?

    fun readAllByUserIdPaged(userId: UUID, index: Int): List<Routine>

    fun readByUserIdAndDayOfWeekAndIsArchivedFalse(userId: UUID, dayOfWeek: DayOfWeek): Routine?

    fun readByUserIdAndDayOfWeek(userId: UUID, dayOfWeek: DayOfWeek): Routine?
}
