package com.info.maeumgagym.application.domain.pickle.entity

import com.info.maeumgagym.application.TableNames
import com.info.maeumgagym.application.domain.base.BaseLongIdEntity
import com.info.maeumgagym.application.domain.user.entity.UserJpaEntity
import javax.persistence.*

@Entity(name = TableNames.PICKLE_LIKE_TABLE)
@Table(
    name = TableNames.PICKLE_LIKE_TABLE,
    indexes = [Index(name = TableNames.PICKLE_LIKE_INDEX, columnList = "pickle_id, user_id", unique = true)]
)
class PickleLikeJpaEntity(
    pickle: PickleJpaEntity,
    user: UserJpaEntity,
    id: Long? = null
) : BaseLongIdEntity(id) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickle_id", updatable = false, nullable = false)
    var pickle: PickleJpaEntity = pickle
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    var user: UserJpaEntity = user
        protected set
}
