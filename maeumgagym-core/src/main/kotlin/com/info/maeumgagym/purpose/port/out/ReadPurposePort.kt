package com.info.maeumgagym.purpose.port.out

import com.info.maeumgagym.purpose.model.Purpose

interface ReadPurposePort {

    fun readById(id: Long): Purpose?
}
