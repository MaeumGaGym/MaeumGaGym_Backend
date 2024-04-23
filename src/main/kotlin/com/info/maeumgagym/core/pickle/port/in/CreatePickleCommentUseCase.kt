package com.info.maeumgagym.core.pickle.port.`in`

import com.info.maeumgagym.core.common.dto.LocationSubjectDto
import com.info.maeumgagym.core.pickle.dto.request.PickleCommentRequest

interface CreatePickleCommentUseCase {
    fun createPickleComment(pickleCommentRequest: PickleCommentRequest, videoId: String): LocationSubjectDto
}
