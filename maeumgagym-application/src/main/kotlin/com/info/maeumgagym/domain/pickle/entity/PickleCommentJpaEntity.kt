package com.info.maeumgagym.domain.pickle.entity

import com.info.maeumgagym.TableNames
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = TableNames.PICKLE_COMMENT_TABLE)
@DiscriminatorValue(value = "comment")
class PickleCommentJpaEntity(
    content: String,
    videoId: String,
    writerId: UUID,
    createdAt: LocalDateTime? = null,
    id: Long? = null
) : PickleCommentMappedEntity(content, videoId, writerId, createdAt, id) {

    @OneToMany(mappedBy = "parentComment", orphanRemoval = true, cascade = [CascadeType.REMOVE])
    var childrenComments: MutableList<PickleReplyJpaEntity> = arrayListOf()
        protected set
}
