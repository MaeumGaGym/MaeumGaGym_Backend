package com.info.maeumgagym.controller.user.dto

import com.info.maeumgagym.user.dto.request.UpdateUserInfoRequest
import com.info.maeumgagym.user.model.GenderModel
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UpdateUserInfoWebRequest(
    @field:NotBlank(message = "nickname은 null, 공백, 띄어쓰기를 허용하지 않습니다.")
    @field:Size(min = 2, max = 10, message = "nickname은 최소 2자 최대 10자까지 가능합니다.")
    val nickname: String,
    @field:DecimalMin(value = "0.0", message = "체중은 0 이상이어야 합니다.")
    @field:DecimalMax(value = "300.0", message = "체중은 300.0 이하여야 합니다.")
    val weight: Double,

    @field:DecimalMin(value = "0.0", message = "신장은 0 이상이어야 합니다.")
    @field:DecimalMax(value = "300.0", message = "신장은 300.0 이하여야 합니다.")
    val height: Double,

    val genderModel: GenderModel
) {
    fun toRequest(): UpdateUserInfoRequest = UpdateUserInfoRequest(
        nickname = nickname,
        height = height,
        weight = weight,
        genderModel = genderModel
    )
}
