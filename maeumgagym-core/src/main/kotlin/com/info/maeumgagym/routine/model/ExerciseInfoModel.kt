package com.info.maeumgagym.routine.model

import com.info.maeumgagym.routine.dto.ExerciseInfoDto

data class ExerciseInfoModel(
    var id: Long,
    var repetitions: Int,
    var sets: Int
) {
    fun toDto(): ExerciseInfoDto = ExerciseInfoDto(
        id = id,
        repetitions = repetitions,
        sets = sets
    )
}
