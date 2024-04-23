package com.info.maeumgagym.core.routine.model

import com.info.maeumgagym.pose.model.Pose
import com.info.maeumgagym.core.routine.dto.response.ExerciseInfoResponse

data class ExerciseInfoHistoryModel(
    var routineHistoryId: Long? = null,
    var pose: Pose,
    var repetitions: Int,
    var sets: Int,
    var id: Long? = null
) {
    companion object {
        fun com.info.maeumgagym.core.routine.model.ExerciseInfoModel.toHistory(): com.info.maeumgagym.core.routine.model.ExerciseInfoHistoryModel =
            com.info.maeumgagym.core.routine.model.ExerciseInfoHistoryModel(
                pose = pose,
                repetitions = repetitions,
                sets = sets,
                id = id
            )
    }

    fun toResponse(): com.info.maeumgagym.core.routine.dto.response.ExerciseInfoResponse =
        com.info.maeumgagym.core.routine.dto.response.ExerciseInfoResponse(
            pose = pose.toInfoResponse(),
            repetitions = repetitions,
            sets = sets
        )
}
