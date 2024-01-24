package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.PickleComment
import com.info.maeumgagym.pickle.model.PickleReply

interface ReadPickleReplyPort {

    fun readAllByParentComment(parentComment: PickleComment, page: Int, size: Int): List<PickleReply>
}
