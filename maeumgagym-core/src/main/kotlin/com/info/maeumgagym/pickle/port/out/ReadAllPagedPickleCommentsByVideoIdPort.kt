package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.PickleComment

interface ReadAllPagedPickleCommentsByVideoIdPort {
    fun readAllPickleCommentsByVideoId(videoId: String, page: Int, size: Int): List<PickleComment>
}
