package com.info.maeumgagym.core.step.service

import com.info.maeumgagym.common.annotation.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.step.dto.response.StepCountResponse
import com.info.maeumgagym.core.step.port.`in`.ReadTodayStepCountUseCase
import com.info.maeumgagym.core.step.port.out.ReadStepPort

@ReadOnlyUseCase
internal class ReadTodayStepCountService(
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readStepPort: ReadStepPort
) : ReadTodayStepCountUseCase {

    override fun readTodayStepCount(): StepCountResponse {
        val user = readCurrentUserPort.readCurrentUser()

        return StepCountResponse(
            readStepPort.readByUserOauthId(user.oauthId)?.numberOfSteps
                ?: 0
        )
    }
}
