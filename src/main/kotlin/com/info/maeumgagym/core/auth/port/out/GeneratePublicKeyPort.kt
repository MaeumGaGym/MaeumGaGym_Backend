package com.info.maeumgagym.core.auth.port.out

import com.info.maeumgagym.core.auth.dto.response.ApplePublicKeysResponse
import java.security.PublicKey

interface GeneratePublicKeyPort {

    fun generatePublicKey(
        tokenHeaders: MutableMap<String?, String?>,
        applePublicKeys: ApplePublicKeysResponse
    ): PublicKey
}
