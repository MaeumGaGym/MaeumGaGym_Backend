package com.info.maeumgagym.core.routine.port.`in`

import com.info.maeumgagym.core.routine.dto.response.RoutineHistoryListResponse
import java.time.LocalDate

interface ReadRoutineHistoryUseCase {

    fun readFromDate(startDate: LocalDate): com.info.maeumgagym.core.routine.dto.response.RoutineHistoryListResponse
}
