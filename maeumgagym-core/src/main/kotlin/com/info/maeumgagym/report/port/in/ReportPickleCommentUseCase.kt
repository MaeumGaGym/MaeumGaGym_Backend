package com.info.maeumgagym.report.port.`in`

import com.info.maeumgagym.report.dto.request.ReportRequest

interface ReportPickleCommentUseCase {

    fun reportPickleComment(request: ReportRequest, id: Long)
}
