package com.info.maeumgagym.core.purpose.port.out

import com.info.maeumgagym.core.purpose.model.Purpose

interface SavePurposePort {
    fun save(purpose: com.info.maeumgagym.core.purpose.model.Purpose): com.info.maeumgagym.core.purpose.model.Purpose
}
