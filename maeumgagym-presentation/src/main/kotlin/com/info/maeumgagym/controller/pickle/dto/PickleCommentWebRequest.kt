package com.info.maeumgagym.controller.pickle.dto

import com.info.maeumgagym.pickle.dto.request.PickleCommentRequest
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class PickleCommentWebRequest(
    @field:NotNull(message = "null일 수 없습니다")
    @field:Size(min = 1, max = 1000, message = "1자 이상 1000자 이하여야 합니다.")
    val content: String,

    @field:NotNull(message = "null일 수 없습니다")
    val pickleId: String,

    val parentId: Long?
) {

    fun toRequest() = PickleCommentRequest(
        content = content,
        parentId = parentId,
        pickleId = pickleId
    )
}
