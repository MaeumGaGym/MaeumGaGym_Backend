package com.info.maeumgagym.domain.report.entity

import com.info.maeumgagym.domain.pickle.entity.PickleJpaEntity
import javax.persistence.*

@Entity
class ReportPickleMapJpaEntity(
    reportId: Long,
    pickleJpaEntity: PickleJpaEntity
) {

    @Id
    @Column(name = "report_id", updatable = false, nullable = false)
    var reportId: Long = reportId
        protected set

    @ManyToOne
    @JoinColumn(name = "pickle_id", updatable = false, nullable = false)
    var pickleJpaEntity: PickleJpaEntity = pickleJpaEntity
        protected set
}
