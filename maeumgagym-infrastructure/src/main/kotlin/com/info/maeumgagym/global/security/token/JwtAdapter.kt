package com.info.maeumgagym.global.security.token


import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.auth.port.out.JwtExpiredCheckPort
import com.info.maeumgagym.global.exception.ExpiredTokenException
import com.info.maeumgagym.global.exception.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtAdapter(
    val jwtProperties: JwtProperties
): GenerateJwtPort, JwtExpiredCheckPort {
    override fun generateToken(email: String): TokenResponse {
        return TokenResponse(
            generateAccessToken(email)
        )
    }

    private fun generateAccessToken(email: String): String{
        val now = Date()
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + jwtProperties.accessExpiredExp * 1000L))
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey)
            .compact()
    }

    override fun getSubjectWithExpiredCheck(token: String): String {
        val body = getBody(token)

        if(body.expiration.before(Date())){
            throw ExpiredTokenException
        } else{
            return body.subject
        }
    }

    private fun getBody(token: String): Claims {
        try {
            return Jwts.parser().setSigningKey(jwtProperties.secretKey).parseClaimsJws(token).body
        } catch (e: JwtException){
            throw InvalidTokenException
        }
    }
}