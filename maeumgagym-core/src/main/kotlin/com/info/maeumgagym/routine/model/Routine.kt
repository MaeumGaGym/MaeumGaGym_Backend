package com.info.maeumgagym.routine.model

import com.info.maeumgagym.routine.dto.ExerciseInfoDto
import com.info.maeumgagym.routine.dto.RoutineStatusDto
import com.info.maeumgagym.routine.dto.response.RoutineResponse
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

data class Routine(
    val id: Long? = null,
    val routineName: String,
    val exerciseInfoModelList: MutableList<ExerciseInfoModel>,
    val dayOfWeeks: MutableSet<DayOfWeek>?,
    val routineStatusModel: RoutineStatusModel,
    val userId: UUID
) {
    fun toResponse(): RoutineResponse =
        RoutineResponse(
            id = id!!,
            routineName = routineName,
            exerciseInfoList = exerciseInfoModelList.map {
                ExerciseInfoDto(
                    exerciseName = it.exerciseName,
                    repetitions = it.repetitions,
                    sets = it.sets
                )
            },
            dayOfWeeks = dayOfWeeks?.map {
                it.getDisplayName(
                    TextStyle.FULL,
                    Locale.KOREA
                )
            },
            routineStatus = routineStatusModel.run {
                RoutineStatusDto(
                    isArchived = isArchived,
                    isShared = isShared
                )
            }
        )

    fun toResponse(isCompleted: Boolean): RoutineResponse =
        RoutineResponse(
            id = id!!,
            routineName = routineName,
            exerciseInfoList = exerciseInfoModelList.map {
                ExerciseInfoDto(
                    exerciseName = it.exerciseName,
                    repetitions = it.repetitions,
                    sets = it.sets
                )
            },
            dayOfWeeks = dayOfWeeks?.map {
                it.getDisplayName(
                    TextStyle.FULL,
                    Locale.KOREA
                )
            },
            routineStatus = routineStatusModel.run {
                RoutineStatusDto(
                    isArchived = isArchived,
                    isShared = isShared
                )
            },
            isCompleted = isCompleted
        )
}
