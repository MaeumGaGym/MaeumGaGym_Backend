package com.info.maeumgagym.application.domain.step.repository

import com.info.maeumgagym.application.domain.step.entity.StepRedisEntity
import org.springframework.data.repository.Repository
@org.springframework.stereotype.Repository
interface StepRepository : Repository<StepRedisEntity, String> {
    fun save(entity: StepRedisEntity): StepRedisEntity

    fun findById(id: String): StepRedisEntity?
}
