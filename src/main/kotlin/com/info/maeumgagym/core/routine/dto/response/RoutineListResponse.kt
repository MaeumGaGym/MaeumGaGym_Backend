package com.info.maeumgagym.core.routine.dto.response

import com.info.maeumgagym.core.user.dto.response.UserResponse

data class RoutineListResponse(
    val userInfo: UserResponse,
    val routineList: List<RoutineResponse>
)
