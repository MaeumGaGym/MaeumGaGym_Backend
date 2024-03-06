package com.info.maeumgagym.step.port.`in`

import com.info.maeumgagym.step.dto.response.StepCountResponse
import com.info.maeumgagym.step.model.Step

/**
 * 금일의 걸음 수 조회 기능
 *
 * [CreateStepUseCase]를 통해 걸음 수 카운팅을 시작한 후, [UpdateStepUseCase]를 통해 걸음 수를 누적
 *
 * 카운팅을 시작하지 않았을 경우 걸음 수를 0으로 응답
 *
 * 현재 [Step]은 금일의 것만 저장
 */
interface ReadTodayStepCountUseCase {

    fun readTodayStepCount(): StepCountResponse
}
