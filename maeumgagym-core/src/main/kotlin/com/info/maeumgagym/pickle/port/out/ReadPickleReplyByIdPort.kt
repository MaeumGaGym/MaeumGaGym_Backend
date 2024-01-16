package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.PickleReply

interface ReadPickleReplyByIdPort {

    fun readPickleReplyById(id: Long): PickleReply?
}
