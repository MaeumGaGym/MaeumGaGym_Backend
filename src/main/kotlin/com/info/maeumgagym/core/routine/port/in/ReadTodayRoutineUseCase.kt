package com.info.maeumgagym.core.routine.port.`in`

import com.info.maeumgagym.core.routine.dto.response.CompletableRoutineListResponse

interface ReadTodayRoutineUseCase {

    fun readTodayRoutine(): CompletableRoutineListResponse
}
