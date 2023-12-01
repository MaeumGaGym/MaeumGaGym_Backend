package com.info.maeumgagym.global.jwt

import com.info.common.WebAdapter
import com.info.maeumgagym.auth.dto.response.TokenResponse
import com.info.maeumgagym.auth.port.out.GenerateJwtPort
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.domain.user.exception.UserNotFoundException
import com.info.maeumgagym.global.env.jwt.JwtProperties
import com.info.maeumgagym.global.exception.ExpiredTokenException
import com.info.maeumgagym.global.exception.InvalidTokenException
import com.info.maeumgagym.user.model.User
import com.info.maeumgagym.user.port.out.FindUserByUUIDPort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import com.info.maeumgagym.auth.port.out.ParsePublicKeyPort
import com.info.maeumgagym.global.security.principle.CustomUserDetailService
import com.info.maeumgagym.global.security.principle.CustomUserDetails
import io.jsonwebtoken.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.security.PublicKey
import java.util.*

@WebAdapter
class JwtAdapter(
    private val jwtProperties: JwtProperties,
    private val findUserByUUIDPort: FindUserByUUIDPort,
    private val customUserDetailService: CustomUserDetailService
) : GenerateJwtPort, ReadCurrentUserPort, ParsePublicKeyPort {
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

    private fun getBody(token: String): Claims {
        try {
            return Jwts.parser().setSigningKey(jwtProperties.secretKey).parseClaimsJws(token).body
        } catch (e: JwtException) {
            throw InvalidTokenException
        }
    }

    override fun readCurrentUser(): User {
        val userId = SecurityContextHolder.getContext().authentication.name
        return findUserByUUIDPort.findUserById(UUID.fromString(userId)) ?: throw UserNotFoundException
    }

    fun getAuthentication(token: String): Authentication {
        val subject = getBody(token).subject

        val authDetails = customUserDetailService.loadUserByUsername(subject) as CustomUserDetails

        return UsernamePasswordAuthenticationToken(authDetails, null, authDetails.authorities)
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
