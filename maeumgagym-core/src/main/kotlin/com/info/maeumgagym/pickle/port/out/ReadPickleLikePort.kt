package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.PickleLike
import com.info.maeumgagym.user.model.User

interface ReadPickleLikePort {
    fun readByVideoIdAndUser(videoId: String, user: User): PickleLike?
}
