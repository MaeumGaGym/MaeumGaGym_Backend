package com.info.maeumgagym.domain.routine.mapper

import com.info.maeumgagym.domain.routine.entity.RoutineStatus
import com.info.maeumgagym.domain.routine.entity.current.ExerciseInfoJpaEntity
import com.info.maeumgagym.domain.routine.entity.current.RoutineJpaEntity
import com.info.maeumgagym.routine.model.Routine
import com.info.maeumgagym.routine.model.RoutineStatusModel
import org.springframework.stereotype.Component

@Component
class RoutineMapper(
    private val exerciseInfoListMapper: ExerciseInfoListMapper
) {
    fun toEntity(routine: Routine): RoutineJpaEntity = routine.run {
        RoutineJpaEntity(
            id = id,
            routineName = routineName,
            dayOfWeeks = dayOfWeeks,
            routineStatus = RoutineStatus(
                isArchived = routineStatusModel.isArchived,
                isShared = routineStatusModel.isShared
            ),
            userId = userId
        )
    }

    fun toDomain(
        routineJpaEntity: RoutineJpaEntity,
        exerciseInfoJpaEntityList: List<ExerciseInfoJpaEntity>
    ): Routine = routineJpaEntity.run {
        Routine(
            id = id,
            routineName = routineName,
            exerciseInfoModelList = exerciseInfoListMapper.toModelList(exerciseInfoJpaEntityList)
                .toMutableList(),
            dayOfWeeks = dayOfWeeks,
            routineStatusModel = RoutineStatusModel(
                isArchived = routineStatus.isArchived,
                isShared = routineStatus.isShared
            ),
            userId = userId
        )
    }
}
