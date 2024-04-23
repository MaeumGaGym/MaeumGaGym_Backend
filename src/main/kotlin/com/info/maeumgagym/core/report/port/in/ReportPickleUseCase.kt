package com.info.maeumgagym.core.report.port.`in`

import com.info.maeumgagym.report.dto.request.ReportRequest

interface ReportPickleUseCase {

    fun reportPickle(request: ReportRequest, videoId: String)
}
