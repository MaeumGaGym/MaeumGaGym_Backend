package com.info.maeumgagym.core.routine.dto.response

import com.info.maeumgagym.core.pose.dto.response.PoseInfoResponse

data class ExerciseInfoResponse(
    var pose: PoseInfoResponse,
    var repetitions: Int,
    var sets: Int,
    var weightKilogram: Int?,
)
