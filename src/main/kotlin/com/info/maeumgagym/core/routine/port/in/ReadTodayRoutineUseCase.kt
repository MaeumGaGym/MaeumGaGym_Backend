package com.info.maeumgagym.core.routine.port.`in`

import com.info.maeumgagym.core.routine.dto.response.RoutineListResponse

interface ReadTodayRoutineUseCase {

    fun readTodayRoutine(): RoutineListResponse
}
