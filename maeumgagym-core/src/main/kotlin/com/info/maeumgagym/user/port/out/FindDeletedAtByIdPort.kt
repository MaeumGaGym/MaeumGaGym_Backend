package com.info.maeumgagym.user.port.out

import java.time.LocalDate
import java.util.*

interface FindDeletedAtByIdPort {

    fun findDeletedAt(id: UUID): LocalDate?
}
