package com.info.maeumgagym.user.port.out

import com.info.maeumgagym.user.model.User

interface DeleteUserPort {
    fun deleteUser(user: User)
}
