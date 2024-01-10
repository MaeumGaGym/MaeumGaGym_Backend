package com.info.maeumgagym.adapter.auth

import com.info.maeumgagym.auth.port.out.*
import com.info.maeumgagym.feign.oauth.apple.AppleClient
import io.jsonwebtoken.Claims
import org.springframework.stereotype.Component

@Component
class AppleAuthAdapter(
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
                appleClient.applePublicKeys().toResponse() // 인코딩 된 애플 공개키 가져오기
            )
        )
}
