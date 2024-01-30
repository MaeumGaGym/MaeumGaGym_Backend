package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.PickleLike

interface SavePickleLikePort {
    fun save(pickleLike: PickleLike): PickleLike
}
