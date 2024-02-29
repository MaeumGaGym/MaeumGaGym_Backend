package com.info.maeumgagym.domain.user.entity

import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
data class PhysicalInfo(
    @Column(name = "user_weight")
    var weight: Double,
    @Column(name = "user_height")
    var height: Double,
    @Enumerated(EnumType.STRING)
    var gender: Gender
)
