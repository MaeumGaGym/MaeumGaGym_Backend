package com.info.maeumgagym.core.routine.service

import com.info.maeumgagym.common.annotation.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.routine.dto.response.RoutineListResponse
import com.info.maeumgagym.core.routine.port.`in`.ReadTodayRoutineUseCase
import com.info.maeumgagym.core.routine.port.out.ReadRoutinePort
import com.info.maeumgagym.core.user.dto.response.UserResponse
import java.time.LocalDate

@ReadOnlyUseCase
class ReadTodayRoutineService(
    private val readRoutinePort: ReadRoutinePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : ReadTodayRoutineUseCase {

    override fun readTodayRoutine(): RoutineListResponse {
        val user = readCurrentUserPort.readCurrentUser()

        val routines = readRoutinePort.readByUserIdAndDayOfWeekAndIsArchivedFalse(
            user.id!!,
            LocalDate.now().dayOfWeek
        )

        if (routines.isEmpty()) {
            throw MaeumGaGymException.NO_CONTENT
        }

        return RoutineListResponse(
            UserResponse(
                nickname = user.nickname,
                profileImage = user.profileImage
            ),
            routines.map { it.toResponse() }
        )
    }
}
