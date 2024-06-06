package com.info.maeumgagym.core.auth.port.out

import com.info.maeumgagym.core.user.model.User

interface ReadCurrentUserPort {
    fun readCurrentUser(): User
}
