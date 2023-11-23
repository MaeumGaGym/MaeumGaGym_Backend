package com.info.maeumgagym.domain.user

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.FindUserPort
import java.util.*

@PersistenceAdapter
class UserPersistenceAdapter() : FindUserPort {
    override fun findUserById(userId: UUID): User {
        TODO("Not yet implemented")
    }
}
