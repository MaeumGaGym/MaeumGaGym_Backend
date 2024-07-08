package com.info.maeumgagym.security.mgtoken

import com.info.maeumgagym.common.exception.AuthenticationException
import com.info.maeumgagym.infrastructure.request.context.CurrentRequestContext
import com.info.maeumgagym.util.UserTestUtils
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpServletRequest
import java.util.*

@SpringBootTest
class MaeumGaGymTokenEncoderDecoderTests @Autowired constructor(
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
    fun 토큰_인코딩() {
        val user = UserTestUtils.TEST_USER
        initCurrentRequestContext()

        Assertions.assertDoesNotThrow {
            encoder.encode(user.oauthId)
        }
    }

    @Test
    fun 정상적인_토큰_디코딩() {
        val user = UserTestUtils.TEST_USER
        initCurrentRequestContext()

        val tokenPair = encoder.encode(user.oauthId)

        Assertions.assertDoesNotThrow {
            decoder.decode(tokenPair.accessToken)
            decoder.decode(tokenPair.refreshToken)
        }
    }

    @Test
    fun Base64_인코딩이_아닌_문자열_디코딩() {
        Assertions.assertThrows(AuthenticationException::class.java) {
            decoder.decode("this is not a maeumgagym token.")
        }
    }

    @Test
    fun Base64로_인코딩됐지만_AES_암호문이_아닌_문자열_디코딩() {
        Assertions.assertThrows(AuthenticationException::class.java) {
            decoder.decode(
                Base64.getEncoder().encodeToString("this is not a maeumgagym token.".encodeToByteArray())
            )
        }
    }
}
