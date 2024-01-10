package com.info.maeumgagym.auth.port.out

import com.info.maeumgagym.user.model.User

interface ReadCurrentUserPort {
    fun readCurrentUser(): User
}
