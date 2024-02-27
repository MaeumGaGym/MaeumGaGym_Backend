package com.info.maeumgagym.domain.user.entity

import javax.persistence.Embeddable

@Embeddable
data class PhysicalInfo(
    var weight: Float,
    var height: Float,
    var gender: Gender
)
