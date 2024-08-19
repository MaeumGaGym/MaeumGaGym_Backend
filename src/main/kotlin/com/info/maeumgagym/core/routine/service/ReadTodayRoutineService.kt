package com.info.maeumgagym.core.routine.service

import com.info.maeumgagym.common.annotation.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.routine.dto.response.CompletableRoutineListResponse
import com.info.maeumgagym.core.routine.port.`in`.ReadTodayRoutineUseCase
import com.info.maeumgagym.core.routine.port.out.ExistsRoutineHistoryPort
import com.info.maeumgagym.core.routine.port.out.ReadRoutinePort
import java.time.LocalDate

@ReadOnlyUseCase
class ReadTodayRoutineService(
    private val readRoutinePort: ReadRoutinePort,
    private val existsRoutineHistoryPort: ExistsRoutineHistoryPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : ReadTodayRoutineUseCase {

    override fun readTodayRoutine(): CompletableRoutineListResponse {
        val user = readCurrentUserPort.readCurrentUser()

        val routines = readRoutinePort.readByUserIdAndDayOfWeekAndIsArchivedFalse(
            user.id!!,
            LocalDate.now().dayOfWeek
        )

        if (routines.isEmpty()) {
            throw MaeumGaGymException.NO_CONTENT
        }

        return CompletableRoutineListResponse(
            routines.map {
                it.toResponse(
                    isCompleted = existsRoutineHistoryPort.existByOriginIdToday(it.id!!)
                )
            }
        )
    }
}
