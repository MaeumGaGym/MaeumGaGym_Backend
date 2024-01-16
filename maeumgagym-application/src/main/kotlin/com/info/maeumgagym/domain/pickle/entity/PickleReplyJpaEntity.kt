package com.info.maeumgagym.domain.pickle.entity

import com.info.maeumgagym.TableNames
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = TableNames.PICKLE_REPLY_TABLE)
@DiscriminatorValue(value = "comment")
class PickleReplyJpaEntity(
    content: String,
    videoId: String,
    writerId: UUID,
    createdAt: LocalDateTime? = null,
    id: Long? = null,
    parentComment: PickleCommentJpaEntity
) : PickleCommentMappedEntity(content, videoId, writerId, createdAt, id) {

    @JoinColumn(name = "parent_commnet_id", updatable = false, nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    var parentComment: PickleCommentJpaEntity = parentComment
        protected set
}
