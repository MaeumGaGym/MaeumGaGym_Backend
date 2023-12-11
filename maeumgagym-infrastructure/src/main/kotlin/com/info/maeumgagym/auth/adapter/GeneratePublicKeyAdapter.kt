package com.info.maeumgagym.auth.adapter

import com.info.maeumgagym.auth.dto.response.ApplePublicKey
import com.info.maeumgagym.auth.dto.response.ApplePublicKeys
import com.info.maeumgagym.auth.port.out.GeneratePublicKeyPort
import com.info.maeumgagym.global.exception.InvalidTokenException
import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException

@Component
class GeneratePublicKeyAdapter : GeneratePublicKeyPort {

    private companion object {
        const val ALG_HEADER_KEY = "alg"
        const val KID_HEADER_KEY = "kid"
    }

    override fun generatePublicKey(
        tokenHeaders: MutableMap<String?, String?>,
        applePublicKeys: ApplePublicKeys
    ): PublicKey {
        val publicKey: ApplePublicKey = applePublicKeys.matchesKey(
            tokenHeaders[ALG_HEADER_KEY]!!,
            tokenHeaders[KID_HEADER_KEY]!!
        ) ?: throw InvalidTokenException

        return try {
            KeyFactory.getInstance(publicKey.kty).generatePublic(publicKey.publicKeySpec())
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException("Apple OAuth 로그인 중 public key 생성에 문제가 발생했습니다.")
        } catch (e: InvalidKeySpecException) {
            throw IllegalStateException("Apple OAuth 로그인 중 public key 생성에 문제가 발생했습니다.")
        }
    }
}
