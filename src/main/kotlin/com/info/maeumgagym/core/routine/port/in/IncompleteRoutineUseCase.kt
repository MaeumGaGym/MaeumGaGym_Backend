package com.info.maeumgagym.core.routine.port.`in`

import java.time.LocalDate

interface IncompleteRoutineUseCase {

    fun incompleteRoutineFromOriginRoutineIdAndDate(originRoutineId: Long, date: LocalDate)
}
