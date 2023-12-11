package com.info.maeumgagym.auth.adapter

import com.info.maeumgagym.auth.port.out.*
import com.info.maeumgagym.feign.oauth.apple.AppleClient
import org.springframework.stereotype.Component

@Component
class AppleAuthAdapter(
    private val appleClient: AppleClient,
    private val appleJwtParsePort: AppleJwtParsePort,
    private val generatePublicKeyPort: GeneratePublicKeyPort,
    private val getJwtBodyPort: GetJwtBodyPort
) : ReadApplePublicKeyPort, ParseAppleTokenPort {

    override fun readPublicKey() = appleClient.applePublicKeys()

    override fun parseIdToken(token: String) = getJwtBodyPort.getJwtBody(
        token,
        generatePublicKeyPort.generatePublicKey(
            appleJwtParsePort.parseHeaders(token),
            readPublicKey()
        )
    )
}
