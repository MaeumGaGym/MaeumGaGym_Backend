package com.info.maeumgagym.auth.port.out

import com.info.maeumgagym.auth.dto.response.ApplePublicKeys
import java.security.PublicKey

interface GeneratePublicKeyPort {

    fun generatePublicKey(
        tokenHeaders: MutableMap<String?, String?>,
        applePublicKeys: ApplePublicKeys
    ): PublicKey
}
