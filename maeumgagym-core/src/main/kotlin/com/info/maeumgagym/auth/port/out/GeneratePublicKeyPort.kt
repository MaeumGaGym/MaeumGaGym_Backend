package com.info.maeumgagym.auth.port.out

import com.info.maeumgagym.auth.dto.response.ApplePublicKeysResponse
import java.security.PublicKey

interface GeneratePublicKeyPort {

    fun generatePublicKey(
        tokenHeaders: MutableMap<String?, String?>,
        applePublicKeys: ApplePublicKeysResponse
    ): PublicKey
}
