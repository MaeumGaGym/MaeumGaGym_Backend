package com.info.maeumgagym.domain.auth.junit5

import com.info.maeumgagym.domain.auth.AuthTestModule
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.module.UserTestModule
import com.info.maeumgagym.global.exception.InvalidTokenException
import com.info.maeumgagym.global.jwt.JwtAdapter
import com.info.maeumgagym.global.jwt.JwtResolver
import com.info.maeumgagym.global.jwt.repository.AccessTokenRepository
import com.info.maeumgagym.global.jwt.repository.RefreshTokenRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class JwtTests @Autowired constructor(
    private val jwtAdapter: JwtAdapter,
    private val jwtResolver: JwtResolver,
    private val accessTokenRepository: AccessTokenRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    private lateinit var user: UserJpaEntity

    @BeforeEach
    fun initialize() {
        user = UserTestModule.createTestUser()
    }

    @Test
    fun generateToken() {
        jwtAdapter.generateTokens(user.oauthId)
        Assertions.assertNotNull(accessTokenRepository.findByIdOrNull(user.oauthId))
        Assertions.assertNotNull(refreshTokenRepository.findByIdOrNull(user.oauthId))
    }

    @Test
    fun reissue() {
        val tokenResponse = jwtAdapter.generateTokens(user.oauthId)
        Assertions.assertDoesNotThrow {
            jwtAdapter.reissue(tokenResponse.refreshToken)
        }
    }

    @Test
    fun reissueWithAccessToken() {
        val tokenResponse = jwtAdapter.generateTokens(user.oauthId)
        Assertions.assertThrows(InvalidTokenException::class.java) {
            jwtAdapter.reissue(tokenResponse.accessToken)
        }
    }

    @Test
    fun reissueWithExpiredToken() {
        val tokenResponse = jwtAdapter.generateTokens(user.oauthId)
        refreshTokenRepository.deleteById(user.oauthId)
        Assertions.assertThrows(InvalidTokenException::class.java) {
            jwtAdapter.reissue(tokenResponse.accessToken)
        }
    }

    @Test
    fun reissueWithUnknownToken() {
        Assertions.assertThrows(InvalidTokenException::class.java) {
            jwtAdapter.reissue("")
        }
    }

    @Test // JwtResolver 성공 상황
    fun resolveWithBearerJwtAccessToken() {
        val request = MockHttpServletRequest()
        val accessToken = jwtAdapter.generateTokens(user.oauthId).accessToken
        request.addHeader(AuthTestModule.TOKEN_HEADER, AuthTestModule.TOKEN_PREFIX + accessToken)
        Assertions.assertEquals(
            jwtResolver.resolveToken(request),
            user.oauthId
        )
        // 환경 변수로 주입된 jwtPrefix가 잘못되었는지 확인
        // header의 이름이 잘못되었는지 확인
    }

    @Test
    fun resolveWithJwtAnyToken() {
        val request = MockHttpServletRequest()
        lateinit var accessToken: String
        lateinit var refreshToken: String
        jwtAdapter.generateTokens(user.oauthId).run {
            accessToken = this.accessToken
            refreshToken = this.refreshToken
        }
        Assertions.assertThrows(InvalidTokenException::class.java) {
            request.addHeader(AuthTestModule.TOKEN_HEADER, accessToken)
            jwtResolver.resolveToken(request)
        }
        Assertions.assertThrows(InvalidTokenException::class.java) {
            request.addHeader(AuthTestModule.TOKEN_HEADER, refreshToken)
            jwtResolver.resolveToken(request)
        }
    }

    @Test
    fun resolveWithBearerRefreshToken() {
        val request = MockHttpServletRequest()
        val refreshToken = jwtAdapter.generateTokens(user.oauthId).refreshToken
        request.addHeader(AuthTestModule.TOKEN_HEADER, AuthTestModule.TOKEN_HEADER + refreshToken)
        Assertions.assertThrows(InvalidTokenException::class.java) {
            jwtResolver.resolveToken(request)
        }
    }

    @Test
    fun resolveWithNothing() {
        Assertions.assertNull(
            jwtResolver.resolveToken(MockHttpServletRequest())
        )
    }
}
