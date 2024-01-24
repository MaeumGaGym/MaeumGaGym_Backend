package com.info.maeumgagym.domain.pickle.repository

import com.info.maeumgagym.domain.pickle.entity.PickleCommentMappedEntity
import org.springframework.data.repository.Repository

@org.springframework.stereotype.Repository
interface PickleCommentMappedRepository : Repository<PickleCommentMappedEntity, Long>
