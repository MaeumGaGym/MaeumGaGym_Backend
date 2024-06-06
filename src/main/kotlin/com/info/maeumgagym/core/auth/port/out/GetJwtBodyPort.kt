package com.info.maeumgagym.core.auth.port.out

import io.jsonwebtoken.Claims
import java.security.PublicKey

/**
 * Jwt 토큰의 Body 부분을 추출
 *
 * @author gurdl0525
 * @since 04-05-2024
 */
interface GetJwtBodyPort {

    fun getJwtBody(token: String, publicKey: PublicKey): Claims
}
