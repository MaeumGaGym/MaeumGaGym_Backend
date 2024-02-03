package com.info.maeumgagym.step.port.out

import com.info.maeumgagym.step.model.Step

interface SaveStepPort {
    fun save(step: Step): Step
}
