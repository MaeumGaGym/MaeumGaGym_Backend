package com.info.maeumgagym.application.domain.routine.mapper

import com.info.maeumgagym.application.domain.routine.entity.history.ExerciseInfoHistoryJpaEntity
import com.info.maeumgagym.core.pose.port.out.ReadPosePort
import com.info.maeumgagym.core.routine.model.ExerciseInfoHistoryModel
import org.springframework.stereotype.Component

@Component
class ExerciseInfoHistoryListMapper(
    private val readPosePort: ReadPosePort
) {

    fun toEntityList(
        modelList: List<ExerciseInfoHistoryModel>,
        routineHistoryId: Long
    ): List<ExerciseInfoHistoryJpaEntity> =
        modelList.map {
            ExerciseInfoHistoryJpaEntity(
                routineHistoryId = routineHistoryId,
                poseId = it.pose.id!!,
                repetitions = it.repetitions,
                sets = it.sets,
                id = it.id
            )
        }.toMutableList()

    fun toModelList(entityList: List<ExerciseInfoHistoryJpaEntity>): List<ExerciseInfoHistoryModel> =
        entityList.map {
            ExerciseInfoHistoryModel(
                routineHistoryId = it.routineHistoryId,
                pose = readPosePort.readById(it.poseId)!!,
                repetitions = it.repetitions,
                sets = it.sets,
                id = it.id
            )
        }.toMutableList()
}
