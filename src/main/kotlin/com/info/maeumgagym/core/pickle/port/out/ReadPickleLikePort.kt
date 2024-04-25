package com.info.maeumgagym.core.pickle.port.out

import com.info.maeumgagym.core.pickle.model.PickleLike
import com.info.maeumgagym.core.user.model.User

interface ReadPickleLikePort {
    fun readByPickleAndUser(pickle: com.info.maeumgagym.core.pickle.model.Pickle, user: User): PickleLike?
}
