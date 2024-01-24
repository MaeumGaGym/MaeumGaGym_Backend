package com.info.maeumgagym.pickle.port.`in`

import com.info.maeumgagym.pickle.dto.response.PickleReplyListResponse

interface LoadAllPickleReplyUseCase {
    fun loadAllPickleReply(parentCommentId: Long, page: Int, size: Int): PickleReplyListResponse
}
