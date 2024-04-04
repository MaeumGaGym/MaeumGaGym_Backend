package com.info.maeumgagym.routine.model

import com.info.maeumgagym.routine.dto.response.ExerciseInfoResponse
import com.info.maeumgagym.routine.dto.response.RoutineHistoryResponse
import java.time.LocalDate
import java.util.*

data class RoutineHistory(
    val id: Long?,
    val userId: UUID,
    val routineName: String,
    val date: LocalDate,
    val exerciseInfoList: MutableList<ExerciseInfoModel>
) {
    fun toResponse(): RoutineHistoryResponse =
        RoutineHistoryResponse(
            id = id!!,
            routineName = routineName,
            exerciseInfoResponseList = exerciseInfoList.map {
                ExerciseInfoResponse(
                    pose = it.pose.toInfoResponse(),
                    repetitions = it.repetitions,
                    sets = it.sets
                )
            },
            date = date
        )
}
