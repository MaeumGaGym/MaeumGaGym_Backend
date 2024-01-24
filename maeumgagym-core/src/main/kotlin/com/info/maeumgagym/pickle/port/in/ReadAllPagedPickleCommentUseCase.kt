package com.info.maeumgagym.pickle.port.`in`

import com.info.maeumgagym.pickle.dto.response.PickleCommentListResponse

interface ReadAllPagedPickleCommentUseCase {
    fun readAllPagedPickleCommentByVideoId(videoId: String, page: Int, size: Int): PickleCommentListResponse
}
