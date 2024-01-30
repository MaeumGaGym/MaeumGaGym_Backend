package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.PickleLike

interface DeletePickleLikePort {

    fun delete(pickleLike: PickleLike)
}
