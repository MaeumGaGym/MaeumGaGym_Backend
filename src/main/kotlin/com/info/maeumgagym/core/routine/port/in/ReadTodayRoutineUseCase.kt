package com.info.maeumgagym.core.routine.port.`in`

import com.info.maeumgagym.core.routine.dto.response.RoutineResponse

interface ReadTodayRoutineUseCase {

    fun readTodayRoutine(): com.info.maeumgagym.core.routine.dto.response.RoutineResponse?
}
