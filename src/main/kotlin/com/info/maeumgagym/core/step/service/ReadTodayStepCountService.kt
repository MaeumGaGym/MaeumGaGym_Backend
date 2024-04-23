package com.info.maeumgagym.core.step.service

import com.info.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort

@ReadOnlyUseCase
internal class ReadTodayStepCountService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readStepPort: com.info.maeumgagym.core.step.port.out.ReadStepPort
) : com.info.maeumgagym.core.step.port.`in`.ReadTodayStepCountUseCase {

    override fun readTodayStepCount(): com.info.maeumgagym.core.step.dto.response.StepCountResponse {
        val user = readCurrentUserPort.readCurrentUser()

        return com.info.maeumgagym.core.step.dto.response.StepCountResponse(
            readStepPort.readByUserOauthId(user.oauthId)?.numberOfSteps
                ?: 0
        )
    }
}
