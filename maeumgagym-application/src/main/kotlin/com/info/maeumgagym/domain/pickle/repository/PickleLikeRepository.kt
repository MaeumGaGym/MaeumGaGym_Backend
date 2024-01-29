package com.info.maeumgagym.domain.pickle.repository

import com.info.maeumgagym.domain.pickle.entity.PickleJpaEntity
import com.info.maeumgagym.domain.pickle.entity.PickleLikeJpaEntity
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface PickleLikeRepository : Repository<PickleLikeJpaEntity, Long> {

    fun save(pickleLikeJpaEntity: PickleLikeJpaEntity): PickleLikeJpaEntity

    fun findByPickleAndUser(pickle: PickleJpaEntity, user: UserJpaEntity): PickleLikeJpaEntity?

    fun delete(pickleLikeJpaEntity: PickleLikeJpaEntity)
}
