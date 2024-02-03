package com.info.maeumgagym.step.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.step.exception.AlreadyExistStepException
import com.info.maeumgagym.step.model.Step
import com.info.maeumgagym.step.port.`in`.CreateStepUseCase
import com.info.maeumgagym.step.port.out.ReadStepPort
import com.info.maeumgagym.step.port.out.SaveStepPort
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDateTime

@UseCase
@Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = [Exception::class])
internal class CreateStepService(
    private val saveStepPort: SaveStepPort,
    private val readStepPort: ReadStepPort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : CreateStepUseCase {
    override fun createStep() {
        val user = readCurrentUserPort.readCurrentUser()
        if (readStepPort.readByUserOauthId(user.oauthId) != null) {
            throw AlreadyExistStepException
        }

        saveStepPort.save(
            Step(
                id = user.oauthId,
                numberOfSteps = 0,
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
