package com.info.maeumgagym.feign.apple.dto

data class ApplePublicKeys(
    val keys: MutableList<ApplePublicKey>
) {

    fun matchesKey(alg: String, kid: String): ApplePublicKey? = keys.singleOrNull { it.alg == alg && it.kid == kid }
}
