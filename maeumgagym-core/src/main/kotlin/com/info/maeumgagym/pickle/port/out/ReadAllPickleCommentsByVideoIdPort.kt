package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.PickleComment

interface ReadAllPickleCommentsByVideoIdPort {
    fun readAllPickleCommentsByVideoId(videoId: String): List<PickleComment>
}
