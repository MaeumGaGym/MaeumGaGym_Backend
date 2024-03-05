package com.info.maeumgagym.controller.daily.dto

import javax.validation.constraints.NotBlank

data class CreateDailyRequest(

    @field:NotBlank(message = "null일 수 없습니다.")
    val title: String?
)
