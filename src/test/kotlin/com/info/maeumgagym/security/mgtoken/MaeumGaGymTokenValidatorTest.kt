package com.info.maeumgagym.security.mgtoken

import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.core.auth.port.out.ReissuePort
import com.info.maeumgagym.infrastructure.request.context.CurrentRequestContext
import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymToken
import com.info.maeumgagym.security.mgtoken.vo.MaeumgagymTokenType
import com.info.maeumgagym.util.UserTestUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@SpringBootTest
class MaeumGaGymTokenValidatorTest @Autowired constructor(
    private val validator: MaeumgagymTokenValidator,

    private val reissuePort: ReissuePort,
    private val revoker: MaeumgagymTokenRevoker,
    private val encoder: MaeumgagymTokenEncoder,
    private val decoder: MaeumgagymTokenDecoder,
    private val currentRequestContext: CurrentRequestContext
) {

    fun initCurrentRequestContext() {
        currentRequestContext.setCurrentRequest(
            MockHttpServletRequest()
        )
    }

    @Test
    fun 정상적인_토큰_인증() {
        val user = UserTestUtils.TEST_USER
        initCurrentRequestContext()

        val stringTokenPair = encoder.encode(user.oauthId)

        val accessToken = decoder.decode(stringTokenPair.accessToken)
        val refreshToken = decoder.decode(stringTokenPair.refreshToken)

        Assertions.assertDoesNotThrow {
            validator.validate(accessToken)
            validator.validate(refreshToken)
        }
    }

    @Test
    fun 만료시간이_잘못_지정된_토큰_인증() {
        val user = UserTestUtils.TEST_USER
        initCurrentRequestContext()

        val accessToken = decoder.decode(
            encoder.encode(user.oauthId).accessToken
        )

        Assertions.assertThrows(AuthenticationException::class.java) {
            validator.validate(
                accessToken.run {
                    MaeumgagymToken(
                        username = username,
                        ip = ip,
                        issuedAt = issuedAt,
                        expireAt = expireAt.plusYears(1),
                        type = type,
                        tokenId = tokenId
                    )
                }
            )
        }
    }

    @Test
    fun 만료시간이_토큰_타입에_맞지_않는_토큰_인증() {
        val user = UserTestUtils.TEST_USER
        initCurrentRequestContext()

        val accessToken = decoder.decode(
            encoder.encode(user.oauthId).accessToken
        )

        Assertions.assertThrows(AuthenticationException::class.java) {
            validator.validate(
                accessToken.run {
                    MaeumgagymToken(
                        username = username,
                        ip = ip,
                        issuedAt = issuedAt,
                        expireAt = expireAt,
                        type = MaeumgagymTokenType.REFRESH_TOKEN,
                        tokenId = tokenId
                    )
                }
            )
        }
    }

    @Test
    fun 발급되지_않은_토큰_인증() {
        val user = UserTestUtils.TEST_USER
        initCurrentRequestContext()

        val accessToken = decoder.decode(
            encoder.encode(user.oauthId).accessToken
        )

        Assertions.assertThrows(AuthenticationException::class.java) {
            validator.validate(
                accessToken.run {
                    MaeumgagymToken(
                        username = username,
                        ip = ip,
                        issuedAt = issuedAt,
                        expireAt = expireAt,
                        type = type,
                        tokenId = UUID.randomUUID().toString()
                    )
                }
            )
        }
    }

    @Test
    fun 무효화된_토큰_인증() {
        val user = UserTestUtils.TEST_USER
        initCurrentRequestContext()

        val accessToken = decoder.decode(
            encoder.encode(user.oauthId).accessToken
        )

        revoker.revoke(accessToken)

        Assertions.assertThrows(AuthenticationException::class.java) {
            validator.validate(
                accessToken.run {
                    MaeumgagymToken(
                        username = username,
                        ip = ip,
                        issuedAt = issuedAt,
                        expireAt = expireAt,
                        type = type,
                        tokenId = tokenId
                    )
                }
            )
        }
    }

    @Test
    fun 재발급_받아_무효화된_토큰_인증() {
        val user = UserTestUtils.TEST_USER
        initCurrentRequestContext()

        val tokenPair = encoder.encode(user.oauthId)
        val accessToken = decoder.decode(tokenPair.accessToken)

        reissuePort.reissue(tokenPair.refreshToken)

        Assertions.assertThrows(AuthenticationException::class.java) {
            validator.validate(
                accessToken.run {
                    MaeumgagymToken(
                        username = username,
                        ip = ip,
                        issuedAt = issuedAt,
                        expireAt = expireAt,
                        type = type,
                        tokenId = tokenId
                    )
                }
            )
        }
    }
}
