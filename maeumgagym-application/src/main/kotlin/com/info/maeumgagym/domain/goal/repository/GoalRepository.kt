package com.info.maeumgagym.domain.goal.repository

import com.info.maeumgagym.domain.goal.entity.GoalJpaEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface GoalRepository : Repository<GoalJpaEntity, Long?> {

    fun save(goalJpaEntity: GoalJpaEntity): GoalJpaEntity
}
