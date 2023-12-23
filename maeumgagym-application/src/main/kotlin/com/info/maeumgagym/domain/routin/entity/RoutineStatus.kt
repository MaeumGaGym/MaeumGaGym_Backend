package com.info.maeumgagym.domain.routin.entity

import javax.persistence.Embeddable

@Embeddable
data class RoutineStatus(
    var isArchived: Boolean,
    var isShared: Boolean
)
