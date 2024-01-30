package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.PickleComment

interface DeletePickleCommentPort {
    fun delete(pickleComment: PickleComment)
}
