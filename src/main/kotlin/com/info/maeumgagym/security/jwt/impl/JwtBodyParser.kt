package com.info.maeumgagym.security.jwt.impl

import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.core.auth.port.out.GetJwtBodyPort
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.security.PublicKey

/**
 * Docs는 상위 타입에 존재.
 *
 * @author gurdl0525
 * @since 04-05-2024
 */
@Component
class JwtBodyParser : GetJwtBodyPort {

    // Apple id_token parsing 함수
    override fun getJwtBody(token: String, publicKey: PublicKey): Claims =
        try {
            // body 불러오기
            Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).body
        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> throw AuthenticationException.EXPIRED_TOKEN
                else -> throw AuthenticationException.INVALID_TOKEN
            }
        }
}
