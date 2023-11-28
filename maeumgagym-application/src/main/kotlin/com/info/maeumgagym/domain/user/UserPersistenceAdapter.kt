package com.info.maeumgagym.domain.user

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.CreateUserPort
import com.info.maeumgagym.user.port.out.FindUserByOAuthIdPort
import com.info.maeumgagym.user.port.out.FindUserPort
import java.util.*

@PersistenceAdapter
class UserPersistenceAdapter() : FindUserPort, CreateUserPort, FindUserByOAuthIdPort {
    override fun findUserById(userId: UUID): User {
        TODO("Not yet implemented")
    }

    override fun createUser(user: User): User {
        TODO("Not yet implemented")
    }

    override fun findUserByOAuthId(oauthId: String): User? {
        TODO("Not yet implemented")
    }
}
