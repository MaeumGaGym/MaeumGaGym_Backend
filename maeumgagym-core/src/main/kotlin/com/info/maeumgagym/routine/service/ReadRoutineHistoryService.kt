package com.info.maeumgagym.routine.service

import com.info.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.routine.dto.response.RoutineHistoryListResponse
import com.info.maeumgagym.routine.port.`in`.ReadRoutineHistoryUseCase
import com.info.maeumgagym.routine.port.out.ReadRoutineHistoryPort
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
