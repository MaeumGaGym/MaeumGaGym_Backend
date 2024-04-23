package com.info.maeumgagym.presentation.controller.pickle.dto

import com.info.maeumgagym.core.pickle.dto.request.PickleCommentRequest
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class PickleCommentWebRequest(
    @field:Size(min = 1, max = 1000, message = "1자 이상 1000자 이하여야 합니다.")
    @field:NotBlank(message = "content는 Null일 수 없습니다.")
    val content: String?
) {

    fun toRequest() = com.info.maeumgagym.core.pickle.dto.request.PickleCommentRequest(
        content = content!!
    )
}
