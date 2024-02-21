package com.info.maeumgagym.purpose.port.out

import com.info.maeumgagym.purpose.model.Purpose

interface SavePurposePort {
    fun save(purpose: Purpose): Purpose
}
