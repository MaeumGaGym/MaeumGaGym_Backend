package com.info.maeumgagym.purpose.port.out

import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

interface DeletePurposePort {

    @Transactional(propagation = Propagation.MANDATORY)
    fun deleteById(id: Long)
}
