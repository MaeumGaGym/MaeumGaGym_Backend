package com.info.maeumgagym.domain.step.repository

import com.info.maeumgagym.domain.step.entity.StepRedisEntity
import org.springframework.data.repository.Repository
@org.springframework.stereotype.Repository
interface StepRepository : Repository<StepRedisEntity, String> {
    fun save(entity: StepRedisEntity): StepRedisEntity

    fun findById(id: String): StepRedisEntity?
}
