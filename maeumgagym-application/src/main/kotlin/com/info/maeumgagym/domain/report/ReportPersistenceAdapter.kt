package com.info.maeumgagym.domain.report

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.report.mapper.ReportMapper
import com.info.maeumgagym.domain.report.repository.ReportRepository
import com.info.maeumgagym.report.model.Report
import com.info.maeumgagym.report.port.out.SaveReportPort

@PersistenceAdapter
internal class ReportPersistenceAdapter(
    private val reportRepository: ReportRepository,
    private val reportMapper: ReportMapper
) : SaveReportPort {

    override fun save(report: Report): Report =
        reportMapper.toDomain(
            reportRepository.save(reportMapper.toEntity(report))
        )
}
