package com.info.maeumgagym.pickle.port.`in`

import com.info.maeumgagym.pickle.dto.response.PickleCommentListResponse

interface ReadAllPickleCommentsUseCase {
    fun readPickleCommentByVideoId(videoId: String): PickleCommentListResponse
}
