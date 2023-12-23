package com.info.maeumgagym.domain.pickle.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import org.springframework.data.domain.Persistable
import java.io.Serializable
import javax.persistence.*

@Entity(name = TableNames.PICKLE_LIKE_TABLE)
@IdClass(PickleLikeJpaEntity.IdClass::class)
class PickleLikeJpaEntity(
    pickle: PickleJpaEntity,
    user: UserJpaEntity
) : Persistable<PickleLikeJpaEntity.IdClass> {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickle_id", updatable = false, nullable = false)
    var pickle: PickleJpaEntity = pickle
        protected set

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    var user: UserJpaEntity = user
        protected set

    data class IdClass(
        val pickle: PickleJpaEntity? = null,
        val user: UserJpaEntity? = null
    ) : Serializable

    override fun getId() = IdClass(pickle, user)

    override fun isNew() = pickle.id == null || user.id == null
}
