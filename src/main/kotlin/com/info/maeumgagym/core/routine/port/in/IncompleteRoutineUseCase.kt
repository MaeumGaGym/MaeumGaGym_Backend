package com.info.maeumgagym.core.routine.port.`in`

import java.time.LocalDate

interface IncompleteRoutineUseCase {

    fun incompleteRoutine(originRoutineId: Long, date: LocalDate)
}
