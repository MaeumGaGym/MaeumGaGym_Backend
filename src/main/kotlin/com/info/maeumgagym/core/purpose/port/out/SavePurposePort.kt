package com.info.maeumgagym.core.purpose.port.out

import com.info.maeumgagym.core.purpose.model.Purpose

interface SavePurposePort {
    fun save(purpose: Purpose): Purpose
}
