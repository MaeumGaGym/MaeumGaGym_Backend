package com.info.maeumgagym.core.user.port.out

import java.util.UUID

interface DeleteUserPort {
    fun deleteById(id: UUID)
}
