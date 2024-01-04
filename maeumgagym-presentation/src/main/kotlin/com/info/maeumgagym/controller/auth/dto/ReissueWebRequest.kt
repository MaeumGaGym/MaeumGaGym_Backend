package com.info.maeumgagym.controller.auth.dto

import javax.validation.constraints.NotBlank

data class ReissueWebRequest(

    @field:NotBlank(message = "null일 수 없습니다")
    val refreshToken: String?
)
