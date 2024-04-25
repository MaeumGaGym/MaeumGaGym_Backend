package com.info.maeumgagym.core.pickle.port.out

import com.info.maeumgagym.core.pickle.model.PickleReply

interface SavePickleReplyPort {

    fun save(pickleReply: PickleReply): PickleReply
}
