package com.info.maeumgagym.core.pickle.port.out

import com.info.maeumgagym.core.pickle.model.PickleComment

interface DeletePickleCommentPort {
    fun delete(pickleComment: PickleComment)
}
