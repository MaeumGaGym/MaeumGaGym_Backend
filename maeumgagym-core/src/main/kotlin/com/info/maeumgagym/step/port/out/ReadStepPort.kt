package com.info.maeumgagym.step.port.out

import com.info.maeumgagym.step.model.Step

interface ReadStepPort {
    fun readByUserOauthId(oauthId: String): Step?
}
