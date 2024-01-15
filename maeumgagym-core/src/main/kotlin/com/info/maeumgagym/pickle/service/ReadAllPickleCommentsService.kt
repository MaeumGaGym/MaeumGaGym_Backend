package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.pickle.dto.response.PickleCommentListResponse
import com.info.maeumgagym.pickle.dto.response.PickleCommentResponse
import com.info.maeumgagym.pickle.exception.PickleNotFoundException
import com.info.maeumgagym.pickle.model.PickleComment
import com.info.maeumgagym.pickle.port.`in`.ReadAllPickleCommentsUseCase
import com.info.maeumgagym.pickle.port.out.ExistsPickleByIdPort
import com.info.maeumgagym.pickle.port.out.ReadAllPickleCommentsByVideoIdPort

@UseCase
class ReadAllPickleCommentsService(
    val existsPickleByIdPort: ExistsPickleByIdPort,
    val readAllPickleCommentsByVideoIdPort: ReadAllPickleCommentsByVideoIdPort
) : ReadAllPickleCommentsUseCase {
    override fun readPickleCommentByVideoId(videoId: String): PickleCommentListResponse {
        if (!existsPickleByIdPort.existsPickleById(videoId)) throw PickleNotFoundException
        val comments =
            convertNestedStructure(readAllPickleCommentsByVideoIdPort.readAllPickleCommentsByVideoId(videoId))
        return PickleCommentListResponse(comments)
    }

    private fun convertNestedStructure(comments: List<PickleComment>): List<PickleCommentResponse> {
        val commentsDto: MutableList<PickleCommentResponse> = arrayListOf()
        val map: MutableMap<Long, PickleCommentResponse> = hashMapOf()
        comments.forEach { c ->
            val commentDto = PickleCommentResponse.toDto(c)
            map[commentDto.id] = commentDto
            if (c.parentComment != null) {
                map[c.parentComment.id]?.childrenComment?.add(commentDto)
            } else {
                commentsDto.add(commentDto)
            }
        }
        return commentsDto
    }
}
