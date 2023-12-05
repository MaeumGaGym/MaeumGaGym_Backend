package com.info.maeumgagym.auth.adapter

import com.info.common.WebAdapter
import com.info.maeumgagym.auth.port.out.*
import com.info.maeumgagym.feign.oauth.apple.AppleClient

@WebAdapter
class AppleAuthAdapter(
    private val appleClient: AppleClient,
    private val appleJwtParsePort: AppleJwtParsePort,
    private val generatePublicKeyPort: GeneratePublicKeyPort,
    private val getJwtBodyPort: GetJwtBodyPort,
    private val readApplePublicKey: ReadApplePublicKeyPort
) : ReadApplePublicKeyPort, ParseAppleTokenPort {

    override fun readPublicKey() = appleClient.applePublicKeys()

    override fun parseIdToken(token: String) = getJwtBodyPort.getJwtBody(
        token,
        generatePublicKeyPort.generatePublicKey(
            appleJwtParsePort.parseHeaders(token),
            readApplePublicKey.readPublicKey()
        )
    )
}
