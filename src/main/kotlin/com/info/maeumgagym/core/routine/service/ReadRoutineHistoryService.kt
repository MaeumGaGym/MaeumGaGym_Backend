package com.info.maeumgagym.core.routine.service

import com.info.maeumgagym.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.routine.dto.response.RoutineHistoryListResponse
import com.info.maeumgagym.core.routine.port.`in`.ReadRoutineHistoryUseCase
import com.info.maeumgagym.core.routine.port.out.ReadRoutineHistoryPort
import java.time.LocalDate

@ReadOnlyUseCase
class ReadRoutineHistoryService(
    private val currentUserPort: ReadCurrentUserPort,
    private val readRoutineHistoryPort: ReadRoutineHistoryPort
) : ReadRoutineHistoryUseCase {

    override fun readFromDate(startDate: LocalDate): RoutineHistoryListResponse {
        val user = currentUserPort.readCurrentUser()

        val endDate: LocalDate = startDate.plusDays(40)

        val histories = readRoutineHistoryPort.readByUserIdAndDateBetweenOrderByDate(user.id!!, startDate, endDate)

        return RoutineHistoryListResponse(
            histories.map { it.toResponse() }
        )
    }
}
