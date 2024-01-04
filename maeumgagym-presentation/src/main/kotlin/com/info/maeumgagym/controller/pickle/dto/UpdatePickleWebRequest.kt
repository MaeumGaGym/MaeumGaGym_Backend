package com.info.maeumgagym.controller.pickle.dto

import com.info.maeumgagym.pickle.dto.request.UpdatePickleRequest
import javax.validation.constraints.NotBlank

data class UpdatePickleWebRequest(

    @field:NotBlank(message = "null일 수 없습니다")
    val title: String?,

    val description: String?,

    val tags: MutableSet<String> = mutableSetOf()
) {

    fun toRequest() = UpdatePickleRequest(
        title!!,
        description,
        tags
    )
}
