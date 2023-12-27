package com.info.maeumgagym.controller.pickle.dto.request

import com.info.maeumgagym.pickle.dto.request.PickleUploadRequest
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class PickleUploadWebRequest(

    @field:NotNull(message = "null일 수 없습니다")
    @field:Max(value = 9999999999, message = "10자리여야 합니다.")
    @field:Min(value = 1000000000, message = "10자리여야 합니다.")
    val videoId: Long?,

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
