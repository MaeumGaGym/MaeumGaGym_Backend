package com.info.maeumgagym.step.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.step.exception.StepNotFoundException
import com.info.maeumgagym.step.port.`in`.UpdateStepUseCase
import com.info.maeumgagym.step.port.out.ReadStepPort
import com.info.maeumgagym.step.port.out.SaveStepPort
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@UseCase
@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class])
internal class UpdateStepService(
    private val saveStepPort: SaveStepPort,
    private val readStepPort: ReadStepPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : UpdateStepUseCase {
    override fun updateStep(numberOfSteps: Int) {
        val user = readCurrentUserPort.readCurrentUser()
        val step = readStepPort.readStep(user.oauthId) ?: throw StepNotFoundException

        saveStepPort.saveStep(step.copy(numberOfSteps = numberOfSteps))
    }
}
