package com.info.maeumgagym.domain.report

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.domain.report.mapper.ReportMapper
import com.info.maeumgagym.domain.report.repository.ReportRepository
import com.info.maeumgagym.report.model.Report
import com.info.maeumgagym.report.port.out.SaveReportPort
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@PersistenceAdapter
internal class ReportPersistenceAdapter(
    private val reportRepository: ReportRepository,
    private val reportMapper: ReportMapper
) : SaveReportPort {

    @Transactional(propagation = Propagation.MANDATORY)
    override fun save(report: Report): Report =
        reportMapper.toDomain(
            reportRepository.save(reportMapper.toEntity(report))
        )
}
