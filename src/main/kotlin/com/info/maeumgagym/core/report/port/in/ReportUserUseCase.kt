package com.info.maeumgagym.core.report.port.`in`

import com.info.maeumgagym.core.report.dto.request.ReportRequest

interface ReportUserUseCase {

    fun reportUser(request: ReportRequest, nickname: String)
}
