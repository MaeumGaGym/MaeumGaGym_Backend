package com.info.maeumgagym.user.port.out

import com.info.maeumgagym.user.model.User

interface SaveUserPort {
    fun saveUser(user: User): User

    fun saveUserAnFlush(user: User): User
}
