package com.info.maeumgagym.core.user.dto.request

import com.info.maeumgagym.core.user.model.GenderModel

data class UpdateUserInfoRequest(
    val nickname: String,
    val weight: Double,
    val height: Double,
    val genderModel: com.info.maeumgagym.core.user.model.GenderModel
)
