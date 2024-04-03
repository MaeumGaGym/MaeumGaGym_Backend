package com.info.maeumgagym.routine.dto

import com.info.maeumgagym.routine.model.ExerciseInfoModel

data class ExerciseInfoDto(
    var id: Long,
    var repetitions: Int,
    var sets: Int
) {
    fun toModel(): ExerciseInfoModel = ExerciseInfoModel(
        id = id,
        repetitions = repetitions,
        sets = sets
    )
}
