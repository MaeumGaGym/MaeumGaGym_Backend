package com.info.maeumgagym.purpose.port.out

interface DeletePurposePort {

    @Transactional(propagation = Propagation.MANDATORY)
    fun deleteById(id: Long)
}
