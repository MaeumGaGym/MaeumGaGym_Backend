package com.info.maeumgagym.report.model

import java.util.*

data class Report(
    val type: ReportType,
    val reason: String,
    val reporterId: UUID,
    val targetId: Any,
    val status: ReportStatus = ReportStatus.ACTIVATED
)
