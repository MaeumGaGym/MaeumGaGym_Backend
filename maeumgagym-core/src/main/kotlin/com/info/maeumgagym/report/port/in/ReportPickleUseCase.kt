package com.info.maeumgagym.report.port.`in`

import com.info.maeumgagym.report.dto.request.ReportRequest

interface ReportPickleUseCase {

    fun reportPickle(request: ReportRequest, videoId: String)
}
