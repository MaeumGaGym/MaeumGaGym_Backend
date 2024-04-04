package com.info.maeumgagym.routine.model

import com.info.maeumgagym.pose.model.Pose
import com.info.maeumgagym.routine.dto.response.ExerciseInfoResponse

data class ExerciseInfoModel(
    var pose: Pose,
    var repetitions: Int,
    var sets: Int
) {
    fun toResponse(): ExerciseInfoResponse = ExerciseInfoResponse(
        pose = pose.toInfoResponse(),
        repetitions = repetitions,
        sets = sets
    )
}
