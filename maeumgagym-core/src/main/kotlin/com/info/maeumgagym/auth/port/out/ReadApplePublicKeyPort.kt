package com.info.maeumgagym.auth.port.out

import com.info.maeumgagym.auth.dto.response.ApplePublicKeys

interface ReadApplePublicKeyPort {

    fun readPublicKey(): ApplePublicKeys
}
