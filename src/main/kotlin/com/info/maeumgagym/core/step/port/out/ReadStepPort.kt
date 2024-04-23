package com.info.maeumgagym.core.step.port.out

import com.info.maeumgagym.core.step.model.Step

interface ReadStepPort {
    fun readByUserOauthId(oauthId: String): com.info.maeumgagym.core.step.model.Step?
}
