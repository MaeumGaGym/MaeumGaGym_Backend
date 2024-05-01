package com.info.maeumgagym.security.jwt.impl

import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.security.jwt.JwtDecoder
import com.info.maeumgagym.security.jwt.JwtEncoder
import com.info.maeumgagym.security.jwt.JwtValidator
import com.info.maeumgagym.security.jwt.env.JwtProperties
import com.info.maeumgagym.security.jwt.vo.Jwt
import com.info.maeumgagym.security.jwt.vo.JwtType
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtManager(
    private val jwtProperties: JwtProperties
) : JwtEncoder,
    JwtDecoder,
    JwtValidator {

     private companion object {
        const val HEADER_TOKEN_TYPE_NAME = "type"

        const val ISSUER_MAEUMGAGYM = "maeumgagym"

        val ENCODE_TYPE = SignatureAlgorithm.HS256
    }

    override fun encodeAccessToken(subject: String): String =
        Jwts.builder()
            .setHeaderParam(HEADER_TOKEN_TYPE_NAME, JwtType.ACCESS_TOKEN.value)
            .setIssuedAt(Date())
            .setExpiration(accessTokenExpiration())
            .setIssuer(ISSUER_MAEUMGAGYM)
            .signWith(ENCODE_TYPE, jwtProperties.secretKey)
            .compact()

    override fun encodeRefreshToken(subject: String): String =
        Jwts.builder()
            .setHeaderParam(HEADER_TOKEN_TYPE_NAME, JwtType.REFRESH_TOKEN.value)
            .setIssuedAt(Date())
            .setExpiration(refreshTokenExpiration())
            .setIssuer(ISSUER_MAEUMGAGYM)
            .signWith(ENCODE_TYPE, jwtProperties.secretKey)
            .compact()

    override fun decode(jwt: String): Jwt {
        val parsedJwt = Jwts.parser()
            .setSigningKey(jwtProperties.secretKey)
            .parse(jwt)

        return Jwt(
            parsedJwt.header,
            parsedJwt.body as Claims,
            JwtType.of(parsedJwt.header[HEADER_TOKEN_TYPE_NAME] as String)
        )
    }

    override fun validate(jwt: Jwt) {
        if (jwt.body.issuer != ISSUER_MAEUMGAGYM) {
            throw SecurityException.INVALID_ISSUER_TOKEN
        }

        if (jwt.body.expiration.before(Date())) {
            throw SecurityException.EXPIRED_TOKEN
        }
    }

    private fun accessTokenExpiration(): Date =
        Date(Date().time + jwtProperties.accessExpiredExp)

    private fun refreshTokenExpiration(): Date =
        Date(Date().time + jwtProperties.refreshExpiredExp)
}
