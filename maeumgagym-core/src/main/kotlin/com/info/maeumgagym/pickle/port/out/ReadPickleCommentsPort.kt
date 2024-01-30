package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.PickleComment

interface ReadPickleCommentsPort {

    fun readAllPagedFromViedoIdAndIndexAndSize(videoId: String, page: Int, size: Int): List<PickleComment>
}
