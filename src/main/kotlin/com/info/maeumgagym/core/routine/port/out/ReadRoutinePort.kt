package com.info.maeumgagym.core.routine.port.out

import com.info.maeumgagym.core.routine.model.Routine
import java.time.DayOfWeek
import java.util.*

interface ReadRoutinePort {

    fun readById(routineId: Long): com.info.maeumgagym.core.routine.model.Routine?

    fun readAllByUserIdPaged(userId: UUID, index: Int): List<com.info.maeumgagym.core.routine.model.Routine>

    fun readByUserIdAndDayOfWeekAndIsArchivedFalse(userId: UUID, dayOfWeek: DayOfWeek): com.info.maeumgagym.core.routine.model.Routine?

    fun readByUserIdAndDayOfWeek(userId: UUID, dayOfWeek: DayOfWeek): com.info.maeumgagym.core.routine.model.Routine?
}
