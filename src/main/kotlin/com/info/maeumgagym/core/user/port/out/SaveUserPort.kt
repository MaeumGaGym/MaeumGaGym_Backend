package com.info.maeumgagym.core.user.port.out

import com.info.maeumgagym.core.user.model.User

interface SaveUserPort {

    fun save(user: User): User
}
