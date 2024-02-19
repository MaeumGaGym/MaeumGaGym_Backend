package com.info.maeumgagym.domain.goal.mapper

import com.info.maeumgagym.domain.goal.entity.GoalJpaEntity
import com.info.maeumgagym.domain.user.mapper.UserMapper
import com.info.maeumgagym.goal.model.Goal
import org.springframework.stereotype.Component

@Component
internal class GoalMapper(
    private val userMapper: UserMapper
) {

    fun toEntity(domain: Goal): GoalJpaEntity = domain.run {
        GoalJpaEntity(
            title = title,
            content = content,
            startDate = startDate,
            endDate = endDate,
            user = userMapper.toEntity(user),
            id = id
        )
    }

    fun toDomain(entity: GoalJpaEntity): Goal = entity.run {
        Goal(
            title = title,
            content = content,
            startDate = startDate,
            endDate = endDate,
            user = userMapper.toDomain(user),
            id = id
        )
    }
}
