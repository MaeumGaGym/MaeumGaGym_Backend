package com.info.maeumgagym.step.port.`in`

import com.info.maeumgagym.step.dto.response.StepCountResponse

interface ReadTodayStepCountUseCase {

    fun readTodayStepCount(): StepCountResponse
}
