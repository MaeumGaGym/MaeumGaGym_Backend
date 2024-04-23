package com.info.maeumgagym.core.pickle.service

import com.info.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.core.pickle.dto.response.PickleReplyListResponse
import com.info.maeumgagym.core.pickle.port.`in`.LoadAllPickleReplyUseCase
import com.info.maeumgagym.core.pickle.port.out.ReadPickleCommentPort
import com.info.maeumgagym.core.pickle.port.out.ReadPickleReplyPort

@ReadOnlyUseCase
internal class ReadAllPickleReplyService(
    private val readPickleReplyPort: ReadPickleReplyPort,
    private val readPickleCommentPort: ReadPickleCommentPort
) : LoadAllPickleReplyUseCase {
    override fun loadAllPickleReply(parentCommentId: Long, page: Int, size: Int): PickleReplyListResponse {
        val replies = readPickleReplyPort.readAllByParentComment(
            readPickleCommentPort.readById(parentCommentId)
                ?: throw BusinessLogicException.PICKLE_COMMENT_NOT_FOUND,
            page,
            size
        )
        return PickleReplyListResponse(replies.map { it.toResponse(it) }
            .toList())
    }
}
