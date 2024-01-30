package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.PickleReply

interface SavePickleReplyPort {

    fun save(pickleReply: PickleReply): PickleReply
}
