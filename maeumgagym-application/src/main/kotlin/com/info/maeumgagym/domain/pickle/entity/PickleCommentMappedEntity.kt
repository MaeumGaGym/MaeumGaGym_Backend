package com.info.maeumgagym.domain.pickle.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseLongIdTimeEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.*

@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE ${TableNames.PICKLE_MAP_TABLE} SET is_deleted = true WHERE id = ?")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "comments_type")
@Entity(name = TableNames.PICKLE_MAP_TABLE)
abstract class PickleCommentMappedEntity(
    content: String,
    videoId: String,
    writerId: UUID,
    createdAt: LocalDateTime? = null,
    id: Long? = null
) : BaseLongIdTimeEntity(id, createdAt) {

    @Column(name = "content", length = 1000, nullable = false)
    var content: String = content
        protected set

    @Column(name = "pickle_id", updatable = false, nullable = false)
    var videoId: String = videoId
        protected set

    @Column(name = "writer_id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    var writerId: UUID = writerId
        protected set

    @Column(name = "is_deleted", nullable = false)
    var isDeleted = false
        protected set
}
