package com.info.maeumgagym.core.auth.dto.response

import org.springframework.util.Base64Utils
import java.math.BigInteger
import java.security.spec.RSAPublicKeySpec

data class ApplePublicKeyResponse(
    val kty: String,
    val kid: String,
    val use: String,
    val alg: String,
    val n: String,
    val e: String
) {

    private fun nBytes() = BigInteger(1, Base64Utils.decodeFromUrlSafeString(this.n))

    private fun eBytes() = BigInteger(1, Base64Utils.decodeFromUrlSafeString(this.e))

    fun publicKeySpec() = RSAPublicKeySpec(nBytes(), eBytes())
}
