package com.info.maeumgagym.core.pickle.port.out

import com.info.maeumgagym.core.pickle.model.PickleComment

interface ReadPickleCommentsPort {

    fun readAllPagedFromViedoIdAndIndexAndSize(videoId: String, page: Int, size: Int): List<PickleComment>
}
