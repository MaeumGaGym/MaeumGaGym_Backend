package com.info.maeumgagym.domain.report.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseLongIdTimeEntity
import com.info.maeumgagym.report.model.ReportStatus
import com.info.maeumgagym.report.model.ReportType
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = TableNames.REPORT_TABLE)
class ReportJpaEntity(
    type: ReportType,
    reason: String,
    status: ReportStatus,
    reporterId: UUID,
    targetId: String
) : BaseLongIdTimeEntity(null, null) {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", updatable = false, nullable = false)
    var type: ReportType = type

    @Column(name = "reason", updatable = false, nullable = false)
    var reason: String = reason
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "status", updatable = true, nullable = false)
    var status: ReportStatus = status
        protected set

    @Column(name = "reporter_id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    var reporterId: UUID = reporterId
        protected set

    @Column(name  = "target_id", updatable = false, nullable = false)
    var targetId: String = targetId
        protected set
}
