package com.info.maeumgagym.routine.dto.response

import com.info.maeumgagym.routine.dto.ExerciseInfoDto
import com.info.maeumgagym.routine.dto.RoutineStatusDto

data class RoutineDetailResponse(
    val routineName: String,
    val routineStatusDto: RoutineStatusDto,
    val exerciseInfoList: List<ExerciseInfoDto>
)
