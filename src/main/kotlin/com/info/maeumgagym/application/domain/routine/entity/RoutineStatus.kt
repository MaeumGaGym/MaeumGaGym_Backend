package com.info.maeumgagym.application.domain.routine.entity

import javax.persistence.Embeddable

@Embeddable
data class RoutineStatus(
    var isArchived: Boolean,
    var isShared: Boolean
)
