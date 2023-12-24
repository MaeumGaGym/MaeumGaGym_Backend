package com.info.maeumgagym.routine.dto.response

import com.info.maeumgagym.user.dto.response.UserResponse

data class RoutineListResponse(
    val user: UserResponse,
    val routineList: List<RoutineResponse>,
)
