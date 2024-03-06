package com.info.maeumgagym.step.port.`in`

import com.info.maeumgagym.step.model.Step

/**
 * 금일의 걸음 수 카운팅에 걸음 수를 추가적으로 누적하는 기능
 *
 * [CreateStepUseCase]를 통해 걸음 수 카운팅을 시작한 후, [UpdateStepUseCase]를 통해 걸음 수를 누적
 *
 * 카운팅을 시작하지 않았을 경우 오류 발생
 *
 * 현재 [Step]은 금일의 것만 저장
 */
interface UpdateStepUseCase {
    fun updateStep(numberOfSteps: Int)
}
