package com.info.maeumgagym.core.routine.model

import com.info.maeumgagym.core.routine.dto.response.ExerciseInfoResponse
import com.info.maeumgagym.core.routine.dto.response.RoutineHistoryResponse
import java.time.LocalDate
import java.util.*

data class RoutineHistory(
    val id: Long?,
    val originId: Long,
    val userId: UUID,
    val routineName: String,
    val date: LocalDate,
    val exerciseInfoHistoryList: List<ExerciseInfoHistoryModel>
) {
    fun toResponse(): RoutineHistoryResponse =
        RoutineHistoryResponse(
            id = id!!,
            routineName = routineName,
            exerciseInfoResponseList = exerciseInfoHistoryList.map {
                ExerciseInfoResponse(
                    pose = it.pose.toInfoResponse(),
                    repetitions = it.repetitions,
                    sets = it.sets,
                    weightKilogram = it.weightKilogram
                )
            },
            date = date
        )
}
