package com.info.maeumgagym.presentation.controller.pickle.dto

import com.info.maeumgagym.core.pickle.dto.request.CreatePickleRequest
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class CreatePickleWebRequest(

    @field:NotBlank(message = "null일 수 없습니다")
    @field:Pattern(regexp = "^[0-9a-f]{8}$")
    val videoId: String?,

    @field:Size(max = 100, message = "1자 이상 100자 이하여야 합니다.")
    @field:NotBlank(message = "null일 수 없습니다")
    val title: String?,

    @field:Size(max = 700, message = "1자 이상 700자 이하여야 합니다.")
    val description: String?,

    @field:Size(max = 30, message = "최대 30개입니다.")
    val tags: MutableSet<String> = mutableSetOf()
) {

    fun toRequest() = com.info.maeumgagym.core.pickle.dto.request.CreatePickleRequest(
        videoId = videoId!!,
        title = title!!,
        description = description,
        tags = tags
    )
}
