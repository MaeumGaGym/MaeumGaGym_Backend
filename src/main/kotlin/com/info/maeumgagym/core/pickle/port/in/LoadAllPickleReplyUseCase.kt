package com.info.maeumgagym.core.pickle.port.`in`

import com.info.maeumgagym.core.pickle.dto.response.PickleReplyListResponse

interface LoadAllPickleReplyUseCase {
    fun loadAllPickleReply(parentCommentId: Long, page: Int, size: Int): PickleReplyListResponse
}
