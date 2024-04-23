package com.info.maeumgagym.core.pickle.port.`in`

import com.info.maeumgagym.core.pickle.dto.response.PickleCommentListResponse

interface ReadAllPagedPickleCommentUseCase {
    fun readAllPagedPickleCommentByVideoId(videoId: String, page: Int, size: Int): PickleCommentListResponse
}
