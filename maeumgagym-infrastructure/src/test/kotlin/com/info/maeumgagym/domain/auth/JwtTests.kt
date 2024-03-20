package com.info.maeumgagym.domain.auth

import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.domain.user.UserTestModule
import com.info.maeumgagym.domain.user.UserTestModule.saveInRepository
import com.info.maeumgagym.domain.user.entity.UserJpaEntity
import com.info.maeumgagym.domain.user.repository.UserRepository
import com.info.maeumgagym.error.TestException
import com.info.maeumgagym.security.jwt.JwtFilter
import com.info.maeumgagym.security.jwt.impl.JwtAdapter
import com.info.maeumgagym.security.jwt.impl.JwtResolverImpl
import com.info.maeumgagym.security.jwt.repository.AccessTokenRepository
import com.info.maeumgagym.security.jwt.repository.RefreshTokenRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockFilterChain
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
internal class JwtTests @Autowired constructor(
    private val jwtFilter: JwtFilter,
    private val jwtAdapter: JwtAdapter,
    private val jwtResolver: JwtResolverImpl,
    private val accessTokenRepository: AccessTokenRepository,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    private lateinit var user: UserJpaEntity

    @BeforeEach
    fun initialize() {
        user = UserTestModule.createTestUser().saveInRepository(userRepository)
    }

    /**
     * @see JwtAdapter.generateTokens
     * @when 성공 상황
     * @fail RefreshTokenRepository에 정상적으로 토큰이 저장되는지 확인
     * @fail AccessTokenRepository에 정상적으로 토큰이 저장되는지 확인
     * @fail 토큰의 만료 시간이 정상적인지 확인
     */
    @Test
    fun generateTokens() {
        val token = jwtAdapter.generateTokens(user.oauthId)
        Assertions.assertNotNull(accessTokenRepository.findById(user.oauthId))
        Assertions.assertNotNull(accessTokenRepository.findByAccessToken(token.first))
        Assertions.assertNotNull(refreshTokenRepository.findById(user.oauthId))
        Assertions.assertNotNull(refreshTokenRepository.findByRfToken(token.second))
    }

    /**
     * @see JwtAdapter.reissue
     * @when 성공 상황
     * @fail RefreshTokenRepository에 정상적으로 토큰이 저장되는지 확인
     */
    @Test
    fun reissue() {
        val tokenResponse = jwtAdapter.generateTokens(user.oauthId)
        Assertions.assertDoesNotThrow {
            jwtAdapter.reissue(tokenResponse.second)
        }
    }

    /**
     * @see JwtAdapter.reissue
     * @when 실패 상황
     * @success RefreshToken이 아닌 AccessToken으로 reissue를 시도했으므로 실패
     * @fail RefreshTokenRepository에 정상적으로 토큰이 저장되는지 확인
     * @fail Exception이 변경되었는지 확인
     */
    @Test
    fun reissueWithAccessToken() {
        val tokenResponse = jwtAdapter.generateTokens(user.oauthId)
        TestException.assertThrowsMaeumGaGymExceptionInstance(AuthenticationException.INVALID_TOKEN) {
            jwtAdapter.reissue(tokenResponse.first)
        }
    }

    /**
     * @see JwtAdapter.reissue
     * @when 실패 상황
     * @success 발급된 적 있으나 만료된(Redis 생명 주기가 끝남) 토큰으로 reissue를 시도했으므로 실패
     * @fail Exception이 변경되었는지 확인
     */
    @Test
    fun reissueWithExpiredToken() {
        val tokenResponse = jwtAdapter.generateTokens(user.oauthId)
        refreshTokenRepository.deleteById(user.oauthId)
        TestException.assertThrowsMaeumGaGymExceptionInstance(AuthenticationException.INVALID_TOKEN) {
            jwtAdapter.reissue(tokenResponse.first)
        }
    }

    /**
     * @see JwtAdapter.reissue
     * @when 실패 상황
     * @success RefreshToken이 아닌 알 수 없는 대상으로 reissue를 시도했으므로 실패
     * @fail Exception이 변경되었는지 확인
     */
    @Test
    fun reissueWithUnknownToken() {
        TestException.assertThrowsMaeumGaGymExceptionInstance(AuthenticationException.INVALID_TOKEN) {
            jwtAdapter.reissue("")
        }
    }

    /**
     * @see JwtResolverImpl.invoke
     * @when 성공 상황
     * @fail 환경 변수로 주입된 jwtPrefix가 잘못되었는지 확인
     * @fail header의 이름이 잘못되었는지 확인
     * @fail AccessTokenRepository에 정상적으로 토큰이 저장되는지 확인
     * */
    @Test
    fun resolve() {
        val accessToken = jwtAdapter.generateTokens(user.oauthId).first
        Assertions.assertEquals(
            jwtResolver(AuthTestModule.TOKEN_PREFIX + accessToken),
            user.oauthId
        )
    }

    /**
     * @see JwtResolverImpl.invoke
     * @when 실패 상황
     * @success "Bearer " 접두사가 존재하지 않아 InvalidTokenException 발생
     * @fail jwtPrefix 환경 변수가 주입되었는지 확인
     */
    @Test
    fun resolveWithJwtAnyToken() {
        lateinit var accessToken: String
        lateinit var refreshToken: String
        jwtAdapter.generateTokens(user.oauthId).run {
            accessToken = this.first
            refreshToken = this.second
        }
        TestException.assertThrowsMaeumGaGymExceptionInstance(AuthenticationException.INVALID_TOKEN) {
            jwtResolver(accessToken)
        }
        TestException.assertThrowsMaeumGaGymExceptionInstance(AuthenticationException.INVALID_TOKEN) {
            jwtResolver(refreshToken)
        }
    }

    /**
     * @see JwtResolverImpl.invoke
     * @when 실패 상황
     * @success 인증 헤더에 AccessToken 대신 RefreshToken이 담겨있으므로 InvalidException 발생
     * @fail AccessTokenRepository에 정상적으로 토큰이 저장되는지 확인
     */
    @Test
    fun resolveWithBearerRefreshToken() {
        val refreshToken = jwtAdapter.generateTokens(user.oauthId).second
        TestException.assertThrowsMaeumGaGymExceptionInstance(AuthenticationException.INVALID_TOKEN) {
            jwtResolver(AuthTestModule.TOKEN_HEADER + refreshToken)
        }
    }

    /**
     * @see JwtResolverImpl.invoke
     * @when 실패 상황
     * @success 인증 관련 헤더가 비어있으므로 유저 정보 대신 null 반환
     * @fail
     */
    @Test
    fun resolveWithNothing() {
        Assertions.assertNull(
            jwtResolver("")
        )
    }

    /**
     * @see JwtFilter.doFilter
     * @when 성공 상황
     * @fail JwtResolver 관련 테스트 확인
     *  @see resolve
     *  @see resolveWithBearerRefreshToken
     *  @see resolveWithJwtAnyToken
     *  @see resolveWithNothing
     */
    @Test
    fun runFilter() {
        val request = MockHttpServletRequest()
        val accessToken = jwtAdapter.generateTokens(user.oauthId).first
        request.addHeader(AuthTestModule.TOKEN_HEADER, AuthTestModule.TOKEN_PREFIX + accessToken)
        jwtFilter.doFilter(
            request,
            MockHttpServletResponse(),
            MockFilterChain()
        )
    }
}
