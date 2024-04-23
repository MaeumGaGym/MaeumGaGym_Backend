package com.info.maeumgagym.core.pickle.service

import com.info.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.common.exception.BusinessLogicException
import com.info.maeumgagym.core.pickle.dto.response.PickleCommentListResponse
import com.info.maeumgagym.core.pickle.port.`in`.ReadAllPagedPickleCommentUseCase
import com.info.maeumgagym.core.pickle.port.out.ExistsPicklePort
import com.info.maeumgagym.core.pickle.port.out.ReadPickleCommentsPort

@ReadOnlyUseCase
internal class ReadAllPagedPickleCommentService(
    private val existsPicklePort: ExistsPicklePort,
    private val readPickleCommentsPort: ReadPickleCommentsPort
) : ReadAllPagedPickleCommentUseCase {
    override fun readAllPagedPickleCommentByVideoId(videoId: String, page: Int, size: Int): PickleCommentListResponse {
        if (!existsPicklePort.existsById(videoId)) {
            throw BusinessLogicException.PICKLE_NOT_FOUND
        }

        val comments = readPickleCommentsPort.readAllPagedFromViedoIdAndIndexAndSize(videoId, page, size)
        val commentList = comments.map { it.toResponse(it) }.toList()
        return PickleCommentListResponse(commentList)
    }
}
