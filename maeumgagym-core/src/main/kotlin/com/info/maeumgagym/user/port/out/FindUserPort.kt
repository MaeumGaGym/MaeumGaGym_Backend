package com.info.maeumgagym.user.port.out

import com.info.maeumgagym.user.model.User
import java.util.UUID

interface FindUserPort {
    fun findUserById(userId: UUID): User
}
