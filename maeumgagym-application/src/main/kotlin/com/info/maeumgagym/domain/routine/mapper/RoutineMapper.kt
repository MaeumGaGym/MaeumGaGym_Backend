package com.info.maeumgagym.domain.routine.mapper

import com.info.maeumgagym.domain.routine.entity.ExerciseInfo
import com.info.maeumgagym.domain.routine.entity.RoutineJpaEntity
import com.info.maeumgagym.domain.routine.entity.RoutineStatus
import com.info.maeumgagym.routine.model.ExerciseInfoModel
import com.info.maeumgagym.routine.model.Routine
import com.info.maeumgagym.routine.model.RoutineStatusModel
import org.springframework.stereotype.Component

@Component
class RoutineMapper {
    fun toEntity(routine: Routine): RoutineJpaEntity = routine.run {
        RoutineJpaEntity(
            id = id,
            routineName = routineName,
            exerciseInfoList = toExerciseInfoList(exerciseInfoModelList),
            dayOfWeeks = dayOfWeeks,
            routineStatus = RoutineStatus(
                isArchived = routineStatusModel.isArchived,
                isShared = routineStatusModel.isShared
            ),
            userId = userId
        )
    }

    fun toDomain(routineJpaEntity: RoutineJpaEntity): Routine = routineJpaEntity.run {
        Routine(
            id = id,
            routineName = routineName,
            exerciseInfoModelList = toExerciseInfoModelList(exerciseInfoList),
            dayOfWeeks = dayOfWeeks,
            routineStatusModel = RoutineStatusModel(
                isArchived = routineStatus.isArchived,
                isShared = routineStatus.isShared
            ),
            userId = userId
        )
    }

    private fun toExerciseInfoList(exerciseInfoList: MutableList<ExerciseInfoModel>) =
        exerciseInfoList.map {
            ExerciseInfo(
                id = it.id,
                repetitions = it.repetitions,
                sets = it.sets
            )
        }.toMutableList()

    private fun toExerciseInfoModelList(exerciseInfoList: MutableList<ExerciseInfo>) =
        exerciseInfoList.map {
            ExerciseInfoModel(
                id = it.id,
                repetitions = it.repetitions,
                sets = it.sets
            )
        }.toMutableList()
}
