package com.info.maeumgagym.step.service

import com.info.common.ReadOnlyUseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.step.dto.response.StepCountResponse
import com.info.maeumgagym.step.port.`in`.ReadTodayStepCountUseCase
import com.info.maeumgagym.step.port.out.ReadStepPort

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
