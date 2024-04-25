package com.info.maeumgagym.core.pickle.port.out

import com.info.maeumgagym.core.pickle.model.PickleLike

interface SavePickleLikePort {
    fun save(pickleLike: PickleLike): PickleLike
}
