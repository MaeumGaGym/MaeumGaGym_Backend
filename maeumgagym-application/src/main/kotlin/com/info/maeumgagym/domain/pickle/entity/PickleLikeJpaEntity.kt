package com.info.maeumgagym.domain.pickle.entity

import com.info.maeumgagym.TableNames
import com.info.maeumgagym.domain.base.BaseLongIdEntity
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import org.springframework.data.domain.Persistable
import java.io.Serializable
import javax.persistence.*

@Entity(name = TableNames.PICKLE_LIKE_TABLE)
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
