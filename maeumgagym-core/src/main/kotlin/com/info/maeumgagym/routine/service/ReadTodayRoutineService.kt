package com.info.maeumgagym.routine.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.routine.dto.response.RoutineResponseWrapper
import com.info.maeumgagym.routine.port.`in`.ReadTodayRoutineUseCase
import com.info.maeumgagym.routine.port.out.ReadRoutinePort
import java.time.LocalDate

@UseCase
class ReadTodayRoutineService(
    private val readRoutinePort: ReadRoutinePort, private val readCurrentUserPort: ReadCurrentUserPort
) : ReadTodayRoutineUseCase {

    override fun readTodayRoutine(): RoutineResponseWrapper = RoutineResponseWrapper(
        readRoutinePort.readByUserIdAndDayOfWeekAndIsArchivedFalse(
            readCurrentUserPort.readCurrentUser().id!!, LocalDate.now().dayOfWeek
        )?.run { toResponse() })
}
