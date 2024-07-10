package com.info.maeumgagym.presentation.controller.routine.dto

import com.info.maeumgagym.core.routine.dto.request.ExerciseInfoRequest
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class ExerciseInfoWebRequest(

    @field:NotNull(message = "null일 수 없습니다")
    @field:Positive(message = "1 이상이어야 합니다.")
    val id: Long,

    @field:NotNull(message = "null일 수 없습니다")
    @field:Positive(message = "1 이상이어야 합니다.")
    val repetitions: Int,

    @field:NotNull(message = "null일 수 없습니다")
    @field:Positive(message = "1 이상이어야 합니다.")
    val sets: Int,

    @field:Positive(message = "1 이상이어야 합니다.")
    val weightKilogram: Int?
) {
    fun toRequest() = ExerciseInfoRequest(
        id = id,
        repetitions = repetitions,
        sets = sets,
        weightKilogram = weightKilogram
    )
}
