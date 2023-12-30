package com.info.maeumgagym.controller.routine.dto.response

import com.info.maeumgagym.routine.dto.ExerciseInfoDto
import com.info.maeumgagym.routine.dto.RoutineStatusDto
import com.info.maeumgagym.routine.dto.response.RoutineResponse

data class RoutineWebResponse(
    val id: Long,
    val routineName: String,
    val exerciseInfoList: List<ExerciseInfoDto>,
    val dayOfWeeks: List<String>?,
    val routineStatus: RoutineStatusDto
) {

    companion object {
        fun toWebResponse(res: RoutineResponse) = RoutineWebResponse(
            res.id,
            res.routineName,
            res.exerciseInfoList,
            res.dayOfWeeks,
            res.routineStatus
        )
    }
}
