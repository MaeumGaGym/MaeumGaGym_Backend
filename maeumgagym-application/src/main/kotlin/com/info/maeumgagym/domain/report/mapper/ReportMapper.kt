package com.info.maeumgagym.domain.report.mapper

import com.info.maeumgagym.domain.report.entity.ReportJpaEntity
import com.info.maeumgagym.report.model.Report
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
