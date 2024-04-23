package com.info.maeumgagym.core.step.port.`in`

import com.info.maeumgagym.core.step.dto.response.StepCountResponse
import com.info.maeumgagym.core.step.model.Step

/**
 * 금일의 걸음 수 조회 기능
 *
 * [CreateStepUseCase]를 통해 걸음 수 카운팅을 시작한 후, [UpdateStepUseCase]를 통해 걸음 수를 누적
 *
 * 카운팅을 시작하지 않았을 경우 걸음 수를 0으로 응답
 *
 * 현재 [Step]은 금일의 것만 저장
 *
 * @see CreateStepUseCase
 * @see UpdateStepUseCase
 *
 * @author HyunSu1768
 */
interface ReadTodayStepCountUseCase {

    fun readTodayStepCount(): com.info.maeumgagym.core.step.dto.response.StepCountResponse
}
