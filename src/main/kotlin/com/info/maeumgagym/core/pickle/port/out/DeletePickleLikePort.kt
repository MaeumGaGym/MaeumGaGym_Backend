package com.info.maeumgagym.core.pickle.port.out

import com.info.maeumgagym.core.pickle.model.PickleLike

interface DeletePickleLikePort {

    fun delete(pickleLike: PickleLike)
}
