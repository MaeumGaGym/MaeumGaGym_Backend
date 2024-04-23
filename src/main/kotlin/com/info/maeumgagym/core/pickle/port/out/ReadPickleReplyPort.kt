package com.info.maeumgagym.core.pickle.port.out

import com.info.maeumgagym.core.pickle.model.PickleComment
import com.info.maeumgagym.core.pickle.model.PickleReply

interface ReadPickleReplyPort {

    fun readById(id: Long): PickleReply?

    fun readAllByParentComment(parentComment: PickleComment, page: Int, size: Int): List<PickleReply>
}
