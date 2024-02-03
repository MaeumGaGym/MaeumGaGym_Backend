package com.info.maeumgagym.domain.step

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.step.mapper.StepMapper
import com.info.maeumgagym.domain.step.repository.StepRepository
import com.info.maeumgagym.step.model.Step
import com.info.maeumgagym.step.port.out.ReadStepPort
import com.info.maeumgagym.step.port.out.SaveStepPort
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@PersistenceAdapter
internal class StepPersistenceAdapter(
    private val stepRepository: StepRepository,
    private val stepMapper: StepMapper
) : SaveStepPort, ReadStepPort {
    override fun readByUserOauthId(oauthId: String): Step? = stepRepository.findById(oauthId)?.let {
        stepMapper.toDomain(
            it
        )
    }

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(step: Step): Step = stepMapper.toDomain(stepRepository.save(stepMapper.toEntity(step)))
}
