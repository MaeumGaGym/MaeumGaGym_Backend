package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.pickle.dto.response.PickleCommentListResponse
import com.info.maeumgagym.pickle.exception.PickleNotFoundException
import com.info.maeumgagym.pickle.port.`in`.ReadAllPagedPickleCommentUseCase
import com.info.maeumgagym.pickle.port.out.ExistsPickleByIdPort
import com.info.maeumgagym.pickle.port.out.ReadAllPagedPickleCommentsByVideoIdPort

@UseCase
internal class ReadAllPagedPickleCommentService(
    private val existsPickleByIdPort: ExistsPickleByIdPort,
    private val readAllPagedPickleCommentsByVideoIdPort: ReadAllPagedPickleCommentsByVideoIdPort
) : ReadAllPagedPickleCommentUseCase {
    override fun readAllPagedPickleCommentByVideoId(videoId: String, page: Int, size: Int): PickleCommentListResponse {
        if (!existsPickleByIdPort.existsPickleById(videoId)) {
            throw PickleNotFoundException
        }

        val comments = readAllPagedPickleCommentsByVideoIdPort.readAllPickleCommentsByVideoId(videoId, page, size)
        val commentList = comments.map { it.toResponse(it) }.toList()
        return PickleCommentListResponse(commentList)
    }
}
