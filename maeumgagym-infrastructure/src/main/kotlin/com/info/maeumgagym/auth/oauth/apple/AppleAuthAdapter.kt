package com.info.maeumgagym.auth.oauth.apple

import com.info.maeumgagym.auth.port.out.AppleJwtParsePort
import com.info.maeumgagym.auth.port.out.GeneratePublicKeyPort
import com.info.maeumgagym.auth.port.out.GetJwtBodyPort
import com.info.maeumgagym.auth.port.out.ParseAppleTokenPort
import com.info.maeumgagym.common.exception.FeignException
import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.auth.oauth.apple.feign.AppleClient
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
                    throw MaeumGaGymException.INTERNAL_SERVER_ERROR
                }
            )
        )
}
