package com.info.maeumgagym.core.report.port.out

import com.info.maeumgagym.report.model.Report

interface SaveReportPort {
    fun save(report: Report): Report
}
