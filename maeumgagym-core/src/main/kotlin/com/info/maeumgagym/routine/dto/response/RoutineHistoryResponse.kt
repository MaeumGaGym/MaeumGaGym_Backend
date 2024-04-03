package com.info.maeumgagym.routine.dto.response

import java.time.LocalDate

data class RoutineHistoryResponse(
    val id: Long,
    val routineName: String,
    val exerciseInfoResponseList: List<ExerciseInfoResponse>,
    val date: LocalDate
)
