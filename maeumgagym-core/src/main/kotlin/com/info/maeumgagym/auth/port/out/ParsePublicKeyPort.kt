package com.info.maeumgagym.auth.port.out

import io.jsonwebtoken.Claims
import java.security.PublicKey

interface ParsePublicKeyPort {

    fun parseClaimsFromPublicKey(token: String, publicKey: PublicKey): Claims
}
