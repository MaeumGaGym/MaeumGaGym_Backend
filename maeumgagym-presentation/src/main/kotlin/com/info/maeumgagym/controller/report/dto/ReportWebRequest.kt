package com.info.maeumgagym.controller.report.dto

import com.info.maeumgagym.report.dto.request.ReportRequest
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class ReportWebRequest(

    @field:Size(max = 1000, message = "1000자 이하여야 합니다")
    @field:NotBlank(message = "null일 수 없습니다")
    val reason: String?
) {
    fun toRequest(): ReportRequest =
        ReportRequest(reason!!)
}
