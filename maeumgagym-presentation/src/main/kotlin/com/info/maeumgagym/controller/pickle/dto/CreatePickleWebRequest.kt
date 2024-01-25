package com.info.maeumgagym.controller.pickle.dto

import com.info.maeumgagym.pickle.dto.request.CreatePickleRequest
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class CreatePickleWebRequest(

    @field:NotBlank(message = "null일 수 없습니다")
    @field:Pattern(regexp = "^[0-9a-f]{8}$")
    val videoId: String?,

    @field:NotBlank(message = "null일 수 없습니다")
    val title: String?,

    val description: String?,

    val tags: MutableSet<String> = mutableSetOf()
) {

    fun toRequest() = CreatePickleRequest(
        videoId = videoId!!,
        title = title!!,
        description = description,
        tags = tags
    )
}
