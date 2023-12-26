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

    override fun parseIdToken(token: String): Claims = getJwtBodyPort.getJwtBody(
        token,
        generatePublicKeyPort.generatePublicKey(
            appleJwtParsePort.parseHeaders(token),
            appleClient.applePublicKeys().toResponse()
        )
    )
}
