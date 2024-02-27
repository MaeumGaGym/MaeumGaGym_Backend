package com.info.maeumgagym.user.dto.request

import com.info.maeumgagym.user.model.GenderModel

data class UpdateUserInfoRequest(
    val nickname: String,
    val weight: Float,
    val height: Float,
    val genderModel: GenderModel
)
