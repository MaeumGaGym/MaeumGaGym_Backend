package com.info.maeumgagym.domain.routine.mapper

import com.info.maeumgagym.domain.routine.entity.ExerciseInfo
import com.info.maeumgagym.domain.routine.entity.RoutineHistoryJpaEntity
import com.info.maeumgagym.pose.port.out.ReadPosePort
import com.info.maeumgagym.routine.model.ExerciseInfoModel
import com.info.maeumgagym.routine.model.RoutineHistory
import org.springframework.stereotype.Component

@Component
class RoutineHistoryMapper(
    private val readPosePort: ReadPosePort
) {
    fun toEntity(model: RoutineHistory): RoutineHistoryJpaEntity = model.run {
        RoutineHistoryJpaEntity(
            id = id,
            exerciseInfoList = toExerciseInfoList(exerciseInfoList),
            userId = userId,
            routineName = routineName,
            date = date
        )
    }

    fun toDomain(entity: RoutineHistoryJpaEntity): RoutineHistory = entity.run {
        RoutineHistory(
            id = id,
            exerciseInfoList = toExerciseInfoModelList(exerciseInfoList),
            userId = userId,
            routineName = routineName,
            date = date
        )
    }

    private fun toExerciseInfoList(exerciseInfoList: MutableList<ExerciseInfoModel>) =
        exerciseInfoList.map {
            ExerciseInfo(
                id = it.pose.id!!,
                repetitions = it.repetitions,
                sets = it.sets
            )
        }.toMutableList()

    private fun toExerciseInfoModelList(exerciseInfoList: MutableList<ExerciseInfo>) =
        exerciseInfoList.map {
            ExerciseInfoModel(
                pose = readPosePort.readById(it.id)!!,
                repetitions = it.repetitions,
                sets = it.sets
            )
        }.toMutableList()
}
