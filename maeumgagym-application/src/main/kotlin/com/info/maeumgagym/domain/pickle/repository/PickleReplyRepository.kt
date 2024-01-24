package com.info.maeumgagym.domain.pickle.repository

import com.info.maeumgagym.domain.pickle.entity.PickleCommentJpaEntity
import com.info.maeumgagym.domain.pickle.entity.PickleReplyJpaEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface PickleReplyRepository : Repository<PickleReplyJpaEntity, Long> {

    fun findAllByParentComment(pickleCommentJpaEntity: PickleCommentJpaEntity): List<PickleReplyJpaEntity>

    fun save(entity: PickleReplyJpaEntity): PickleReplyJpaEntity
}
