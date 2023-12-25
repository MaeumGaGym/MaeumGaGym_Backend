package com.info.maeumgagym.domain.pickle.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.converter.StringAttributeConverter
import com.info.maeumgagym.domain.base.BaseLongIdTimeEntity
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE ${TableNames.PICKLE_TABLE} SET is_deleted = true WHERE id = ?")
@Entity(name = TableNames.PICKLE_TABLE)
class PickleJpaEntity(
    description: String? = null,
    title: String,
    uploader: UserJpaEntity,
    videoUrl: String,
    likeCount: Long = 0,
    tags: MutableSet<String> = mutableSetOf(),
    createdAt: LocalDateTime? = null,
    id: Long? = null
) : BaseLongIdTimeEntity(id, createdAt) {

    @Column(name = "title", nullable = false)
    var title: String = title
        protected set

    @Column(name = "description", nullable = true)
    var description: String? = description
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploader_id", nullable = false)
    var uploader: UserJpaEntity = uploader
        protected set

    @Column(name = "video_url", columnDefinition = "CHAR(52)", nullable = false)
    var videoUrl: String = videoUrl
        protected set

    @Column(name = "like_count", nullable = false)
    var likeCount: Long = likeCount
        protected set

    @Convert(converter = StringAttributeConverter::class)
    @Column(name = "tags", length = 1000, nullable = false)
    var tags: MutableSet<String> = tags
        get() = field.toMutableSet()
        protected set

    @Column(name = "is_deleted", nullable = false)
    var isDeleted = false
        protected set
}
