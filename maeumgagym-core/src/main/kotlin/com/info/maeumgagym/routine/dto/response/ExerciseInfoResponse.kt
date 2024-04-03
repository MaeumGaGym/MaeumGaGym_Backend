package com.info.maeumgagym.routine.dto.response

import com.info.maeumgagym.pose.dto.response.PoseInfoResponse

data class ExerciseInfoResponse(
    var pose: PoseInfoResponse,
    var repetitions: Int,
    var sets: Int
)
