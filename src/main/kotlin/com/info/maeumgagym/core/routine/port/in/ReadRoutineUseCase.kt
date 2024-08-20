package com.info.maeumgagym.core.routine.port.`in`

import com.info.maeumgagym.core.routine.dto.response.CompletableRoutineResponse

interface ReadRoutineUseCase {
    fun readFromId(routineId: Long): CompletableRoutineResponse
}
