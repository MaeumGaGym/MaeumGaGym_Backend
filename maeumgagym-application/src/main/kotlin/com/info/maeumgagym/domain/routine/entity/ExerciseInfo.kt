package com.info.maeumgagym.domain.routine.entity

import javax.persistence.Embeddable

@Embeddable
data class ExerciseInfo(
    var exerciseName: String,
    var repetitions: Int,
    var sets: Int
)
