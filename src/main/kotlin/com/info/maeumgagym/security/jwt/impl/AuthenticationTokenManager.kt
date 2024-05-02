package com.info.maeumgagym.security.jwt.impl

import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.security.jwt.AuthenticationTokenDecoder
import com.info.maeumgagym.security.jwt.AuthenticationTokenEncoder
import com.info.maeumgagym.security.jwt.AuthenticationTokenValidator
import com.info.maeumgagym.security.jwt.env.JwtProperties
import com.info.maeumgagym.security.jwt.vo.AuthenticationToken
import com.info.maeumgagym.security.jwt.vo.AuthenticationTokenType
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

/**
 * AuthenticationToken 관련 모듈의 구현체.
 *
 * 각 함수는 상위 인터페이스의 Docs를 참고.
 *
 * @see AuthenticationTokenEncoder
 * @see AuthenticationTokenDecoder
 * @see AuthenticationTokenValidator
 *
 * @author Daybreak312
 * @since 01-05-2024
 */
@Component
class AuthenticationTokenManager(
    private val jwtProperties: JwtProperties
) : AuthenticationTokenEncoder,
    AuthenticationTokenDecoder,
    AuthenticationTokenValidator {

    private companion object {
        const val HEADER_TOKEN_TYPE_NAME = "type"

        const val ISSUER_MAEUMGAGYM = "maeumgagym"

        val ENCODE_TYPE = SignatureAlgorithm.HS256
    }

    override fun encodeAccessToken(subject: String): String =
        Jwts.builder()
            .setHeaderParam(HEADER_TOKEN_TYPE_NAME, AuthenticationTokenType.ACCESS_TOKEN.value)
            .setIssuedAt(Date())
            .setExpiration(accessTokenExpiration())
            .setIssuer(ISSUER_MAEUMGAGYM)
            .signWith(ENCODE_TYPE, jwtProperties.secretKey)
            .compact()

    override fun encodeRefreshToken(subject: String): String =
        Jwts.builder()
            .setHeaderParam(HEADER_TOKEN_TYPE_NAME, AuthenticationTokenType.REFRESH_TOKEN.value)
            .setIssuedAt(Date())
            .setExpiration(refreshTokenExpiration())
            .setIssuer(ISSUER_MAEUMGAGYM)
            .signWith(ENCODE_TYPE, jwtProperties.secretKey)
            .compact()

    override fun decode(token: String): AuthenticationToken {
        val parsedJwt = Jwts.parser()
            .setSigningKey(jwtProperties.secretKey)
            .parse(resolveJwtPrefix(token))

        return parsedJwt.toJwtValue()
    }

    override fun decode(token: String, key: String): AuthenticationToken {
        val parsedJwt = Jwts.parser()
            .setSigningKey(key)
            .parse(resolveJwtPrefix(token))

        return parsedJwt.toJwtValue()
    }

    override fun validate(authenticationToken: AuthenticationToken) {
        if (authenticationToken.body.issuer != ISSUER_MAEUMGAGYM) {
            throw SecurityException.INVALID_ISSUER_TOKEN
        }

        if (authenticationToken.body.expiration.before(Date())) {
            throw SecurityException.EXPIRED_TOKEN
        }
    }

    private fun accessTokenExpiration(): Date =
        Date(Date().time + jwtProperties.accessExpiredExp)

    private fun refreshTokenExpiration(): Date =
        Date(Date().time + jwtProperties.refreshExpiredExp)

    private fun resolveJwtPrefix(jwt: String): String {
        if (!jwt.startsWith(jwtProperties.prefix)) {
            throw SecurityException.NOT_JWT_TOKEN
        }

        return jwt.substring(jwtProperties.prefix.length).trimStart()
    }

    private fun io.jsonwebtoken.Jwt<Header<*>, Any>.toJwtValue() = AuthenticationToken(
        this.header,
        this.body as Claims,
        AuthenticationTokenType.of(this.header[HEADER_TOKEN_TYPE_NAME] as String)
    )
}
