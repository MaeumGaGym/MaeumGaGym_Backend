package com.info.maeumgagym.core.routine.model

import com.info.maeumgagym.core.pose.model.Pose
import com.info.maeumgagym.core.routine.dto.response.ExerciseInfoResponse

data class ExerciseInfoHistoryModel(
    var routineHistoryId: Long? = null,
    var pose: Pose,
    var repetitions: Int,
    var sets: Int,
    var id: Long? = null
) {
    companion object {
        fun ExerciseInfoModel.toHistory(): ExerciseInfoHistoryModel =
            ExerciseInfoHistoryModel(
                pose = pose,
                repetitions = repetitions,
                sets = sets,
                id = id
            )
    }

    fun toResponse(): ExerciseInfoResponse =
        ExerciseInfoResponse(
            pose = pose.toInfoResponse(),
            repetitions = repetitions,
            sets = sets
        )
}
