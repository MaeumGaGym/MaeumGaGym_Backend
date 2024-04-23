package com.info.maeumgagym.core.pickle.port.out

import com.info.maeumgagym.core.pickle.model.PickleReply

interface DeletePickleReplyPort {
    fun delete(pickleReply: PickleReply)
}
