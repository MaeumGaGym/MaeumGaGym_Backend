package com.info.maeumgagym.global.jwt

import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.auth.port.out.ParsePublicKeyPort
import com.info.maeumgagym.global.env.jwt.JwtProperties
import com.info.maeumgagym.global.exception.ExpiredTokenException
import com.info.maeumgagym.global.exception.InvalidTokenException
import com.info.maeumgagym.global.security.principle.CustomUserDetailService
import com.info.maeumgagym.global.security.principle.CustomUserDetails
import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.PublicKey
import java.util.*

@Component
class JwtAdapter(
    val jwtProperties: JwtProperties,
    private val customUserDetailService: CustomUserDetailService
) : GenerateJwtPort, ParsePublicKeyPort {

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

    fun getAuthentication(token: String): Authentication {
        val subject = getBody(token).subject

        val authDetails = customUserDetailService.loadUserByUsername(subject) as CustomUserDetails

        return UsernamePasswordAuthenticationToken(authDetails, null, authDetails.authorities)
    }

    private fun getBody(token: String): Claims {
        try {
            return Jwts.parser().setSigningKey(jwtProperties.secretKey).parseClaimsJws(token).body
        } catch (e: JwtException) {
            when (e) {
                is ExpiredJwtException -> throw ExpiredTokenException
                else -> throw InvalidTokenException
            }
        }
    }

    override fun parseClaimsFromPublicKey(token: String, publicKey: PublicKey): Claims {
        return try {
            Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).body
        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> throw ExpiredTokenException
                else -> throw InvalidTokenException
            }
        }
    }
}
