package com.info.maeumgagym.domain.pickle.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import org.springframework.data.domain.Persistable
import java.io.Serializable
import javax.persistence.*

@Entity(name = TableNames.PICKLE_LIKE_TABLE)
@IdClass(PickleLikeJpaEntity.IdClass::class)
class PickleLikeJpaEntity(
    pickleId: String,
    user: UserJpaEntity
) : Persistable<PickleLikeJpaEntity.IdClass> {
    @Id
    @Column(name = "pickle_id", updatable = false, nullable = false)
    var pickleId: String = pickleId
        protected set

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    var user: UserJpaEntity = user
        protected set

    data class IdClass(
        val pickleId: String,
        val user: UserJpaEntity
    ) : Serializable

    override fun getId() = IdClass(pickleId, user)

    override fun isNew() = user.id == null
}
