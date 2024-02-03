package com.info.maeumgagym.routine.port.`in`

import com.info.maeumgagym.routine.dto.response.RoutineResponseWrapper

interface ReadTodayRoutineUseCase {

    fun readTodayRoutine(): RoutineResponseWrapper
}
