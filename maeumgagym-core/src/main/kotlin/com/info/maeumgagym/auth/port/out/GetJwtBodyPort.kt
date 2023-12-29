package com.info.maeumgagym.auth.port.out

import io.jsonwebtoken.Claims
import java.security.PublicKey

interface GetJwtBodyPort {

    fun getJwtBody(token: String, publicKey: PublicKey): Claims
}
