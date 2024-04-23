package com.info.maeumgagym.core.report.port.`in`

import com.info.maeumgagym.report.dto.request.ReportRequest

interface ReportPickleReplyUseCase {

    fun reportPickleReply(request: ReportRequest, id: Long)
}
