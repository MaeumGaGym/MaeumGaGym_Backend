package com.info.maeumgagym.pickle.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class PickleUploadRequest(

    @field:NotBlank(message = "null일 수 없습니다")
    val title: String?,

    val description: String?,

    val tags: MutableSet<String> = mutableSetOf(),

    @field:NotBlank(message = "null일 수 없습니다")
    @field:Pattern(regexp = "https://([\\w-]+)/([\\w-]+)/index.m3u8", message = "올바른 url이 아닙니다")
    val videoUrl: String?
)
