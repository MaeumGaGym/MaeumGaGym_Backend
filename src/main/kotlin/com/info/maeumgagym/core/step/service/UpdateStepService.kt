package com.info.maeumgagym.core.step.service

import com.info.maeumgagym.common.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.core.step.model.Step
import com.info.maeumgagym.core.step.port.`in`.UpdateStepUseCase
import com.info.maeumgagym.core.step.port.out.ReadStepPort
import com.info.maeumgagym.core.step.port.out.SaveStepPort
import java.time.Duration
import java.time.LocalDateTime

@UseCase
internal class UpdateStepService(
    private val saveStepPort: SaveStepPort,
    private val readStepPort: ReadStepPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : UpdateStepUseCase {
    override fun updateStep(numberOfSteps: Int) {
        val user = readCurrentUserPort.readCurrentUser()
        saveStepPort.save(
            Step(
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
