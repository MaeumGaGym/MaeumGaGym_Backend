package com.info.maeumgagym.presentation.controller.purpose.dto

import com.info.maeumgagym.core.common.convertor.LocalDateConvertor
import com.info.maeumgagym.core.purpose.dto.request.CreatePurposeRequest
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class CreatePurposeWebRequest(

    @field:NotBlank(message = "null일 수 없습니다.")
    val title: String,

    @field:NotBlank(message = "null일 수 없습니다.")
    val content: String,

    @field:NotBlank(message = "null일 수 없습니다.")
    @field:Pattern(regexp = "^[1-2][0-9]{3}-[0-1][0-9]-[0-3][0-9]$")
    val startDate: String,

    @field:NotBlank(message = "null일 수 없습니다.")
    @field:Pattern(regexp = "^[1-2][0-9]{3}-[0-1][0-9]-[0-3][0-9]$")
    val endDate: String
) {
    fun toRequest(): CreatePurposeRequest =
        CreatePurposeRequest(
            title = title,
            content = content,
            startDate = LocalDateConvertor.stringToLocalDate(startDate),
            endDate = LocalDateConvertor.stringToLocalDate(endDate)
        )
}
