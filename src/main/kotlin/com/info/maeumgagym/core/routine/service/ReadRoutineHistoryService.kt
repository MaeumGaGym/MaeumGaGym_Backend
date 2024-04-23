package com.info.maeumgagym.core.routine.service

import com.info.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.routine.port.`in`.ReadRoutineHistoryUseCase
import com.info.maeumgagym.routine.port.out.ReadRoutineHistoryPort
import java.time.LocalDate

@ReadOnlyUseCase
class ReadRoutineHistoryService(
    private val currentUserPort: ReadCurrentUserPort,
    private val readRoutineHistoryPort: ReadRoutineHistoryPort
) : ReadRoutineHistoryUseCase {

    override fun readFromDate(startDate: LocalDate): com.info.maeumgagym.core.routine.dto.response.RoutineHistoryListResponse {
        val user = currentUserPort.readCurrentUser()

        val endDate: LocalDate = startDate.plusDays(40)

        val histories = readRoutineHistoryPort.readByUserIdAndDateBetweenOrderByDate(user.id!!, startDate, endDate)

        return com.info.maeumgagym.core.routine.dto.response.RoutineHistoryListResponse(
            histories.map { it.toResponse() }
        )
    }
}
