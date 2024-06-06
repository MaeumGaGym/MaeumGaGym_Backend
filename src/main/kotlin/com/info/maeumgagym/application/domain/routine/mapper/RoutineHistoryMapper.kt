package com.info.maeumgagym.application.domain.routine.mapper

import com.info.maeumgagym.application.domain.routine.entity.history.ExerciseInfoHistoryJpaEntity
import com.info.maeumgagym.application.domain.routine.entity.history.RoutineHistoryJpaEntity
import com.info.maeumgagym.core.routine.model.RoutineHistory
import org.springframework.stereotype.Component

@Component
class RoutineHistoryMapper(
    val exerciseInfoHistoryListMapper: ExerciseInfoHistoryListMapper
) {
    fun toEntity(model: RoutineHistory): RoutineHistoryJpaEntity = model.run {
        RoutineHistoryJpaEntity(
            id = id,
            originId = originId,
            userId = userId,
            routineName = routineName,
            date = date
        )
    }

    fun toDomain(
        entity: RoutineHistoryJpaEntity,
        exerciseInfoHistoryJpaEntityList: List<ExerciseInfoHistoryJpaEntity>
    ): RoutineHistory = entity.run {
        RoutineHistory(
            id = id,
            originId = originId,
            exerciseInfoHistoryList =
            exerciseInfoHistoryListMapper.toModelList(exerciseInfoHistoryJpaEntityList).toMutableList(),
            userId = userId,
            routineName = routineName,
            date = date
        )
    }
}
