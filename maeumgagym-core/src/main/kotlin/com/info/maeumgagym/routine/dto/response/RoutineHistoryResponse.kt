package com.info.maeumgagym.routine.dto.response

import com.info.maeumgagym.routine.dto.ExerciseInfoDto
import java.time.LocalDate

data class RoutineHistoryResponse(
    val id: Long,
    val routineName: String,
    val exerciseInfoDtoList: List<ExerciseInfoDto>,
    val date: LocalDate
)
