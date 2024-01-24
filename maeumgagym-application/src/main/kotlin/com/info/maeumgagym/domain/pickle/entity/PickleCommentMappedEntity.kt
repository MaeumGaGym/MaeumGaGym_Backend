package com.info.maeumgagym.domain.pickle.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseLongIdTimeEntity
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE ${TableNames.PICKLE_MAP_TABLE} SET is_deleted = true WHERE id = ?")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "comments_type")
@Entity(name = TableNames.PICKLE_MAP_TABLE)
abstract class PickleCommentMappedEntity(
    content: String,
    videoId: String,
    writer: UserJpaEntity,
    createdAt: LocalDateTime? = null,
    id: Long? = null
) : BaseLongIdTimeEntity(id, createdAt) {

    @Column(name = "content", length = 1000, nullable = false)
    var content: String = content
        protected set

    @Column(name = "pickle_id", updatable = false, nullable = false)
    var videoId: String = videoId
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer", nullable = false)
    var writer: UserJpaEntity = writer
        protected set

    @Column(name = "is_deleted", nullable = false)
    var isDeleted = false
        protected set
}
