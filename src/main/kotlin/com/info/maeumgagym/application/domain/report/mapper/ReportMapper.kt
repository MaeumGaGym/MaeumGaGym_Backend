package com.info.maeumgagym.application.domain.report.mapper

import com.info.maeumgagym.application.domain.report.entity.ReportJpaEntity
import com.info.maeumgagym.core.report.model.Report
import org.springframework.stereotype.Component

@Component
class ReportMapper {

    fun toEntity(domain: Report): ReportJpaEntity = domain.run {
        ReportJpaEntity(
            type = type,
            reason = reason,
            status = status,
            reporterId = reporterId,
            targetId = targetId.toString()
        )
    }

    fun toDomain(entity: ReportJpaEntity): Report = entity.run {
        Report(
            type = type,
            reason = reason,
            reporterId = reporterId,
            targetId = targetId,
            status = status
        )
    }
}
