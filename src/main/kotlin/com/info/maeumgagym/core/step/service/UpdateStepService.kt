package com.info.maeumgagym.core.step.service

import com.info.common.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import java.time.Duration
import java.time.LocalDateTime

@UseCase
internal class UpdateStepService(
    private val saveStepPort: com.info.maeumgagym.core.step.port.out.SaveStepPort,
    private val readStepPort: com.info.maeumgagym.core.step.port.out.ReadStepPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : com.info.maeumgagym.core.step.port.`in`.UpdateStepUseCase {
    override fun updateStep(numberOfSteps: Int) {
        val user = readCurrentUserPort.readCurrentUser()
        saveStepPort.save(
            com.info.maeumgagym.core.step.model.Step(
                id = user.oauthId,
                numberOfSteps = numberOfSteps,
                ttl = secondsUntilTomorrow()
            )
        )
    }

    private fun secondsUntilTomorrow(): Long {
        val now = LocalDateTime.now()
        val tomorrowStart = now.toLocalDate().plusDays(1).atStartOfDay()
        return Duration.between(now, tomorrowStart).toSeconds()
    }
}
