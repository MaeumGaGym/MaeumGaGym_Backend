package com.info.maeumgagym.user.port.out

import java.util.UUID

interface DeleteUserPort {
    fun deleteById(id: UUID)
}
