package com.info.maeumgagym.domain.pickle.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseLongIdTimeEntity
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE ${TableNames.PICKLE_COMMENT_TABLE} SET is_deleted = true WHERE id = ?")
@Entity(name = TableNames.PICKLE_COMMENT_TABLE)
class PickleCommentJpaEntity(
    content: String,
    pickle: PickleJpaEntity,
    writer: UserJpaEntity,
    replies: MutableList<PickleReplyJpaEntity> = mutableListOf(),
    createdAt: LocalDateTime? = null,
    id: Long? = null
) : BaseLongIdTimeEntity(id, createdAt) {

    @Column(name = "content", length = 1000, nullable = false)
    var content: String = content
        protected set

    @ManyToOne
    @JoinColumn(name = "pickle_id", updatable = false, nullable = false)
    var pickle: PickleJpaEntity = pickle
        protected set

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var replies: MutableList<PickleReplyJpaEntity> = replies

    @ManyToOne
    @JoinColumn(name = "writer_id", updatable = false, nullable = false)
    var writer: UserJpaEntity = writer
        protected set

    @Column(name = "is_deleted", nullable = false)
    var isDeleted = false
        protected set
}
