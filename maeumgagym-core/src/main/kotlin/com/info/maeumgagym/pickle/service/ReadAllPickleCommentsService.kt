package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.pickle.dto.response.PickleCommentListResponse
import com.info.maeumgagym.pickle.exception.PickleNotFoundException
import com.info.maeumgagym.pickle.port.`in`.ReadAllPickleCommentsUseCase
import com.info.maeumgagym.pickle.port.out.ExistsPickleByIdPort
import com.info.maeumgagym.pickle.port.out.ReadAllPickleCommentsByVideoIdPort

@UseCase
internal class ReadAllPickleCommentsService(
    private val existsPickleByIdPort: ExistsPickleByIdPort,
    private val readAllPickleCommentsByVideoIdPort: ReadAllPickleCommentsByVideoIdPort
) : ReadAllPickleCommentsUseCase {
    override fun readPickleCommentByVideoId(videoId: String): PickleCommentListResponse {
        if (!existsPickleByIdPort.existsPickleById(videoId)) {
            throw PickleNotFoundException
        }

        val comments = readAllPickleCommentsByVideoIdPort.readAllPickleCommentsByVideoId(videoId)
        val commentList = comments.map { it.toResponse(it) }.toList()
        return PickleCommentListResponse(commentList)
    }
}
