package com.info.maeumgagym.core.step.port.out

import com.info.maeumgagym.core.step.model.Step

interface SaveStepPort {
    fun save(step: com.info.maeumgagym.core.step.model.Step): com.info.maeumgagym.core.step.model.Step
}
