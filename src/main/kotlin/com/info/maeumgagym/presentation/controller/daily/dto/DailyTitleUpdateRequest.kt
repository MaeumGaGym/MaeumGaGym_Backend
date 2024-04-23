package com.info.maeumgagym.presentation.controller.daily.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class DailyTitleUpdateRequest(
    @field:NotBlank(message = "null일 수 없습니다.")
    @field:Size(max = 15, message = "최대 길이 제한은 15자입니다.")
    val title: String?
)
