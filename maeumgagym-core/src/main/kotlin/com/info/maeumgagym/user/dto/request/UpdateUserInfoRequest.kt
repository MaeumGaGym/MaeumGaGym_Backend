package com.info.maeumgagym.user.dto.request

import com.info.maeumgagym.user.model.GenderModel

data class UpdateUserInfoRequest(
    val nickname: String,
    val weight: Double,
    val height: Double,
    val genderModel: GenderModel
)
