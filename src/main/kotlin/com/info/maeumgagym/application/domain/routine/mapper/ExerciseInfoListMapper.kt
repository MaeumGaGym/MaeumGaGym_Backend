package com.info.maeumgagym.application.domain.routine.mapper

import com.info.maeumgagym.application.domain.routine.entity.current.ExerciseInfoJpaEntity
import com.info.maeumgagym.core.pose.port.out.ReadPosePort
import com.info.maeumgagym.core.routine.model.ExerciseInfoModel
import org.springframework.stereotype.Component

@Component
class ExerciseInfoListMapper(
    private val readPosePort: ReadPosePort
) {

    fun toEntityList(modelList: List<ExerciseInfoModel>, routineId: Long): List<ExerciseInfoJpaEntity> =
        modelList.map {
            ExerciseInfoJpaEntity(
                routineId = it.routineId ?: routineId,
                poseId = it.pose.id!!,
                repetitions = it.repetitions,
                sets = it.sets,
                id = it.id
            )
        }.toMutableList()

    fun toModelList(entityList: List<ExerciseInfoJpaEntity>): List<ExerciseInfoModel> =
        entityList.map {
            ExerciseInfoModel(
                routineId = it.routineId,
                pose = readPosePort.readById(it.poseId)!!,
                repetitions = it.repetitions,
                sets = it.sets,
                id = it.id
            )
        }.toMutableList()
}
