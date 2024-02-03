package com.info.maeumgagym.routine.port.out

import com.info.maeumgagym.routine.model.Routine
import java.time.DayOfWeek
import java.util.*

interface ReadRoutinePort {

    fun readById(routineId: Long): Routine?

    fun readAllByUserId(userId: UUID): List<Routine>

    fun readByUserIdAndDayOfWeekAndIsArchivedFalse(userId: UUID, dayOfWeek: DayOfWeek): Routine?
}
