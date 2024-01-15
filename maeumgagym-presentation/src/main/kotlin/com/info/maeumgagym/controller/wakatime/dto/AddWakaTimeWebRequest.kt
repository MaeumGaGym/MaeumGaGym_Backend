package com.info.maeumgagym.controller.wakatime.dto

import javax.validation.constraints.NotNull

data class AddWakaTimeWebRequest(

    @field:NotNull(message = "seconds가 null일 수 없습니다.")
    val seconds: Long?
)
