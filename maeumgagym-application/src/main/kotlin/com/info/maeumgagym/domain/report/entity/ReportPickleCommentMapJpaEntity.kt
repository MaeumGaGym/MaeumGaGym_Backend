package com.info.maeumgagym.domain.report.entity

import com.info.maeumgagym.domain.pickle.entity.PickleCommentJpaEntity
import javax.persistence.*

@Entity
class ReportPickleCommentMapJpaEntity(
    reportId: Long,
    pickleCommentJpaEntity: PickleCommentJpaEntity
) {

    @Id
    @Column(name = "report_id", updatable = false, nullable = false)
    var reportId: Long = reportId
        protected set

    @ManyToOne
    @JoinColumn(name = "pickle_comment_id", updatable = false, nullable = false)
    var pickleCommentJpaEntity: PickleCommentJpaEntity = pickleCommentJpaEntity
        protected set
}
