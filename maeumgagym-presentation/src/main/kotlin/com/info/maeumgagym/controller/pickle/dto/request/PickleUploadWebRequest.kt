package com.info.maeumgagym.controller.pickle.dto.request

import com.info.maeumgagym.pickle.dto.request.PickleUploadRequest
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class PickleUploadWebRequest(

    @field:NotBlank(message = "null일 수 없습니다")
    @field:Pattern(regexp = "^[0-9a-fA-F]{8}$")
    val videoId: String?,

    @field:NotBlank(message = "null일 수 없습니다")
    val title: String?,

    val description: String?,

    val tags: MutableSet<String> = mutableSetOf()
) {

    fun toRequest() = PickleUploadRequest(
        videoId = videoId!!,
        title = title!!,
        description = description,
        tags = tags
    )
}
