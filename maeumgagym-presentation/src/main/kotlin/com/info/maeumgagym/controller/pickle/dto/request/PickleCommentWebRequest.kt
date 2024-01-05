package com.info.maeumgagym.controller.pickle.dto.request

import com.info.maeumgagym.pickle.dto.request.PickleCommentRequest
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class PickleCommentWebRequest(
    @field:NotNull(message = "null일 수 없습니다")
    @field:Max(value = 1000, message = "최대 1000 자리여야 합니다.")
    @field:Min(value = 1, message = "최소 1자리여야 합니다.")
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
