package com.info.maeumgagym.core.user.port.out

import com.info.maeumgagym.core.user.model.User

interface SaveUserPort {

    fun save(user: com.info.maeumgagym.core.user.model.User): com.info.maeumgagym.core.user.model.User
}
