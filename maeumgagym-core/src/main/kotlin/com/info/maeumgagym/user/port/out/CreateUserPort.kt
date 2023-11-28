package com.info.maeumgagym.user.port.out

import com.info.maeumgagym.user.model.User

interface CreateUserPort {
    fun createUser(user: User): User
}
