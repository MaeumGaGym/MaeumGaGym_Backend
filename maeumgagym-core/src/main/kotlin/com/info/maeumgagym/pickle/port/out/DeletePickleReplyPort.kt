package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.PickleReply

interface DeletePickleReplyPort {
    fun delete(pickleReply: PickleReply)
}
