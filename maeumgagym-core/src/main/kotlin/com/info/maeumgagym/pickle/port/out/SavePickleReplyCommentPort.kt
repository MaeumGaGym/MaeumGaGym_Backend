package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.PickleReply

interface SavePickleReplyCommentPort {
    fun savePickleReplyComment(pickleReply: PickleReply): PickleReply
}
