package com.info.maeumgagym.domain.user

import com.info.common.PersistenceAdapter
import com.info.maeumgagym.auth.port.out.CreateUserPort

@PersistenceAdapter
class CreateUserAdapter : CreateUserPort {

    override fun createUser(sub: String, name: String, profilePath: String?) {
        TODO("Not yet implemented")
    }
}
