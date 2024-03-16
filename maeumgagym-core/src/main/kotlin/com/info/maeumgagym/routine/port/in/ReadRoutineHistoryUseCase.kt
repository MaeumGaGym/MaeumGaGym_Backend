package com.info.maeumgagym.routine.port.`in`

import com.info.maeumgagym.routine.dto.response.RoutineHistoryListResponse
import java.time.LocalDate

interface ReadRoutineHistoryUseCase {

    fun readFromDate(startDate: LocalDate): RoutineHistoryListResponse
}
