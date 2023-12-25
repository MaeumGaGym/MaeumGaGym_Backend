package com.info.maeumgagym.pickle.dto.request

import javax.validation.constraints.NotBlank

data class UpdatePickleRequest(

    @field:NotBlank(message = "null일 수 없습니다")
    val title: String?,

    val description: String?,

    val tags: MutableSet<String> = mutableSetOf()
)
