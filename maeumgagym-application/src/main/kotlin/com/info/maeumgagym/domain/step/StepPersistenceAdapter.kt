package com.info.maeumgagym.domain.step

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.step.mapper.StepMapper
import com.info.maeumgagym.domain.step.repository.StepRepository
import com.info.maeumgagym.step.model.Step
import com.info.maeumgagym.step.port.out.ReadStepPort
import com.info.maeumgagym.step.port.out.SaveStepPort

@PersistenceAdapter
internal class StepPersistenceAdapter(
    private val stepRepository: StepRepository,
    private val stepMapper: StepMapper
): SaveStepPort, ReadStepPort {
    override fun readStep(userId: String): Step? = stepRepository.findById(userId)?.let { stepMapper.toDomain(it) }

    override fun saveStep(step: Step): Step = stepMapper.toDomain(stepRepository.save(stepMapper.toEntity(step)))
}
