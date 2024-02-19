package com.info.maeumgagym.domain.goal

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.goal.mapper.GoalMapper
import com.info.maeumgagym.domain.goal.repository.GoalRepository
import com.info.maeumgagym.goal.model.Goal
import com.info.maeumgagym.goal.port.out.SaveGoalPort

@PersistenceAdapter
internal class GoalPersistenceAdapter(
    private val goalRepository: GoalRepository,
    private val goalMapper: GoalMapper
) : SaveGoalPort {


    override fun save(goal: Goal): Goal =
        goalMapper.toDomain(
            goalRepository.save(goalMapper.toEntity(goal))
        )
}
