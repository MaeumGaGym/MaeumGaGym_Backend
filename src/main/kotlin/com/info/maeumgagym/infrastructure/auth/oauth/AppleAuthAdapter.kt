package com.info.maeumgagym.infrastructure.auth.oauth

import com.info.maeumgagym.core.auth.port.out.AppleJwtParsePort
import com.info.maeumgagym.core.auth.port.out.GeneratePublicKeyPort
import com.info.maeumgagym.core.auth.port.out.GetJwtBodyPort
import com.info.maeumgagym.core.auth.port.out.ParseAppleTokenPort
import com.info.maeumgagym.core.common.exception.FeignException
import com.info.maeumgagym.core.common.exception.MaeumGaGymException
import com.info.maeumgagym.core.common.exception.SecurityException
import com.info.maeumgagym.infrastructure.external.feign.oauth.apple.AppleClient
import io.jsonwebtoken.Claims
import org.springframework.stereotype.Component

@Component
internal class AppleAuthAdapter(
    private val appleClient: AppleClient,
    private val appleJwtParsePort: AppleJwtParsePort,
    private val generatePublicKeyPort: GeneratePublicKeyPort,
    private val getJwtBodyPort: GetJwtBodyPort
) : ParseAppleTokenPort {

    override fun parseIdToken(token: String): Claims =
        getJwtBodyPort.getJwtBody( // 애플 id_token 파싱해서 Claims반환 하는 함수 호춯
            token,
            generatePublicKeyPort.generatePublicKey( // 애플 공개키 가져오는 함수 호출
                appleJwtParsePort.parseHeaders(token), // json형식으로 인증정보 가져오기
                try {
                    appleClient.applePublicKeys().toResponse() // 인코딩 된 애플 공개키 가져오기
                } catch (e: FeignException) {
                    throw if (e.message == FeignException.FEIGN_UNAUTHORIZED.message ||
                        e.message == FeignException.FEIGN_FORBIDDEN.message
                    ) {
                        SecurityException.INVALID_TOKEN
                    } else {
                        MaeumGaGymException.INTERNAL_SERVER_ERROR
                    }
                }
            )
        )
}
