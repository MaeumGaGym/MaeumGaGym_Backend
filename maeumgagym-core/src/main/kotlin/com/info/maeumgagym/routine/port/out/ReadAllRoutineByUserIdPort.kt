package com.info.maeumgagym.routine.port.out

import com.info.maeumgagym.routine.dto.response.RoutineListResponse
import java.util.UUID

interface ReadAllRoutineByUserIdPort {
    fun readAllRoutineByUserId(userId: UUID): RoutineListResponse
}
