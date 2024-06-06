package com.info.maeumgagym.application.domain.step.mapper

import com.info.maeumgagym.application.domain.step.entity.StepRedisEntity
import org.springframework.stereotype.Component

@Component
class StepMapper {
    fun toEntity(domain: com.info.maeumgagym.core.step.model.Step): StepRedisEntity = domain.run {
        StepRedisEntity(
            id = id,
            numberOfSteps = numberOfSteps,
            ttl = ttl
        )
    }

    fun toDomain(entity: StepRedisEntity): com.info.maeumgagym.core.step.model.Step = entity.run {
        com.info.maeumgagym.core.step.model.Step(
            id = id,
            numberOfSteps = numberOfSteps,
            ttl = ttl
        )
    }
}
