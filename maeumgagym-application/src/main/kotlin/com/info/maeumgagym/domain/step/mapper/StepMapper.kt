package com.info.maeumgagym.domain.step.mapper

import com.info.maeumgagym.domain.step.entity.StepRedisEntity
import com.info.maeumgagym.step.model.Step
import org.springframework.stereotype.Component

@Component
class StepMapper {
    fun toEntity(domain: Step): StepRedisEntity = domain.run {
        StepRedisEntity(
            id = id,
            numberOfSteps = numberOfSteps,
            ttl = ttl
        )
    }

    fun toDomain(entity: StepRedisEntity): Step = entity.run {
        Step(
            id = id,
            numberOfSteps = numberOfSteps,
            ttl = ttl
        )
    }
}
