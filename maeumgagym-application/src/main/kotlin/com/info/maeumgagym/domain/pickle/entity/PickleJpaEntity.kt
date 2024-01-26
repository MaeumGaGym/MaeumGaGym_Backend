package com.info.maeumgagym.domain.pickle.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.converter.StringAttributeConverter
import com.info.maeumgagym.domain.base.BaseTimeEntity
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE ${TableNames.PICKLE_TABLE} SET is_deleted = true WHERE id = ?")
@Table(
    name = TableNames.PICKLE_TABLE,
    indexes = [Index(name = TableNames.PICKLE_TAG_INDEX, columnList = "tags")]
)
class PickleJpaEntity(
    videoId: String,
    title: String,
    description: String? = null,
    uploader: UserJpaEntity,
    likeCount: Long = 0,
    commentCount: Long = 0,
    tags: MutableSet<String> = mutableSetOf(),
    createdAt: LocalDateTime? = null
) : BaseTimeEntity(createdAt) {

    @Id
    @Column(name = "video_id", length = 8, nullable = false)
    var videoId: String = videoId
        protected set

    @Column(name = "title", length = 100, nullable = false)
    var title: String = title
        protected set

    @Column(name = "description", length = 700, nullable = true)
    var description: String? = description
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id", nullable = false)
    var uploader: UserJpaEntity = uploader
        protected set

    @Column(name = "like_count", nullable = false)
    var likeCount: Long = likeCount
        protected set

    @Column(name = "comment_count", nullable = false)
    var commentCount: Long = commentCount
        protected set

    @Convert(converter = StringAttributeConverter::class)
    @Column(name = "tags", length = 400, nullable = false)
    var tags: MutableSet<String> = tags
        get() = field.toMutableSet()
        protected set

    @Column(name = "is_deleted", nullable = false)
    var isDeleted = false
        protected set
}
