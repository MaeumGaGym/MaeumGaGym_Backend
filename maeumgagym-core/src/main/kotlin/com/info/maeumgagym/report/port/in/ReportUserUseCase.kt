package com.info.maeumgagym.report.port.`in`

import com.info.maeumgagym.report.dto.request.ReportRequest

interface ReportUserUseCase {

    fun reportUser(request: ReportRequest, nickname: String)
}
