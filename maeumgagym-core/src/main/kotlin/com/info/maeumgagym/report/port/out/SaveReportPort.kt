package com.info.maeumgagym.report.port.out

import com.info.maeumgagym.report.model.Report

interface SaveReportPort {
    fun save(report: Report): Report
}
