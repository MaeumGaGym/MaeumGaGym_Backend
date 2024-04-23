package com.info.maeumgagym.core.report.port.`in`

import com.info.maeumgagym.core.report.dto.request.ReportRequest

interface ReportPickleCommentUseCase {

    fun reportPickleComment(request: ReportRequest, id: Long)
}
