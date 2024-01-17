package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.PickleComment
import com.info.maeumgagym.pickle.model.PickleReply

interface ReadAllReplyByParentCommentPort {
    fun readAllPickleReplyByParentComment(parentComment: PickleComment): List<PickleReply>
}
