package com.info.maeumgagym.application.domain.pickle.entity

import com.info.maeumgagym.application.TableNames
import com.info.maeumgagym.application.domain.user.entity.UserJpaEntity
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.OneToMany

@Entity(name = TableNames.PICKLE_COMMENT_TABLE)
@DiscriminatorValue(value = "comment")
class PickleCommentJpaEntity(
    content: String,
    videoId: String,
    writer: UserJpaEntity,
    createdAt: LocalDateTime? = null,
    id: Long? = null
) : PickleCommentMappedEntity(content, videoId, writer, createdAt, id) {

    @OneToMany(mappedBy = "parentComment", orphanRemoval = true, cascade = [CascadeType.REMOVE])
    var childrenComments: MutableList<PickleReplyJpaEntity> = arrayListOf()
        protected set
}
