package com.info.maeumgagym.core.pickle.port.`in`

import com.info.maeumgagym.core.common.dto.LocationSubjectDto
import com.info.maeumgagym.core.pickle.dto.request.PickleCommentRequest

interface CreatePickleReplyCommentUseCase {
    fun createPickleReplyComment(
        pickleCommentRequest: PickleCommentRequest,
        videoId: String,
        parentId: Long
    ): LocationSubjectDto
}
