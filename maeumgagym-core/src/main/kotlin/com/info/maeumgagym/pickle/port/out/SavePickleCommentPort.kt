package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.PickleComment

interface SavePickleCommentPort {

    fun save(pickleComment: PickleComment): PickleComment
}
