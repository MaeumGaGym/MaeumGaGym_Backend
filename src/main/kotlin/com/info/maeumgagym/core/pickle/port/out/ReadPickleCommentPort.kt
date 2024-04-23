package com.info.maeumgagym.core.pickle.port.out

import com.info.maeumgagym.core.pickle.model.PickleComment

interface ReadPickleCommentPort {

    fun readById(pickleCommentId: Long): PickleComment?
}
