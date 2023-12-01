package com.info.maeumgagym.auth.adapter

import com.info.common.WebAdapter
import com.info.maeumgagym.auth.port.out.ReadApplePublicKeyPort
import com.info.maeumgagym.feign.oauth.apple.AppleClient

@WebAdapter
class AppleAuthAdapter(
    private val appleClient: AppleClient
): ReadApplePublicKeyPort {

    override fun readPublicKey() = appleClient.applePublicKeys()
}
