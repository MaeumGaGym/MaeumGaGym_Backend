package com.info.maeumgagym.global.security.token

import com.info.common.WebAdapter
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.auth.port.out.JwtExpiredCheckPort
import com.info.maeumgagym.global.env.jwt.JwtProperties
import com.info.maeumgagym.global.exception.ExpiredTokenException
import com.info.maeumgagym.global.exception.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@WebAdapter
class JwtAdapter(
    val jwtProperties: JwtProperties
) : GenerateJwtPort, JwtExpiredCheckPort {
    override fun generateToken(userId: UUID): TokenResponse {
        return TokenResponse(
            generateAccessToken(userId)
        )
    }

    private fun generateAccessToken(userId: UUID): String {
        val now = Date()
        return Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(now)
            .setExpiration(Date(now.time + jwtProperties.accessExpiredExp * 1000L))
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
            .compact()
    }

    override fun getSubjectWithExpiredCheck(token: String): String {
        val body = getBody(token)

        if (body.expiration.before(Date())) {
            throw ExpiredTokenException
        } else {
            return body.subject
        }
    }

    private fun getBody(token: String): Claims {
        try {
            return Jwts.parser().setSigningKey(jwtProperties.secretKey).parseClaimsJws(token).body
        } catch (e: JwtException) {
            throw InvalidTokenException
        }
    }
}
