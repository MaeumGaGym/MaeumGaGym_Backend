package com.info.maeumgagym.auth.oauth

import com.info.maeumgagym.auth.dto.response.ApplePublicKeyResponse
import com.info.maeumgagym.auth.dto.response.ApplePublicKeysResponse
import com.info.maeumgagym.auth.port.out.GeneratePublicKeyPort
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.common.exception.SecurityException
import org.springframework.stereotype.Component
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.spec.InvalidKeySpecException

@Component
internal class GeneratePublicKeyAdapter : GeneratePublicKeyPort {

    private companion object {
        const val ALG_HEADER_KEY = "alg"
        const val KID_HEADER_KEY = "kid"
    }

    override fun generatePublicKey(
        tokenHeaders: MutableMap<String?, String?>,
        applePublicKeys: ApplePublicKeysResponse
    ): PublicKey {
        // id_token에 사용된 공개키와 애플에서 가져온 공개키가 match 되는지 확인
        val publicKey: ApplePublicKeyResponse = applePublicKeys.matchesKey(
            tokenHeaders[ALG_HEADER_KEY]!!, // token의 alg값
            tokenHeaders[KID_HEADER_KEY]!! // token의 kid값
        ) ?: throw SecurityException.INVALID_TOKEN

        return try {
            KeyFactory.getInstance(publicKey.kty).generatePublic(publicKey.publicKeySpec()) // 공개키 발급
        } catch (e: NoSuchAlgorithmException) {
            throw BusinessLogicException(500, "Apple OAuth 로그인 중 public key 생성에 문제가 발생했습니다.")
        } catch (e: InvalidKeySpecException) {
            throw BusinessLogicException(500, "Apple OAuth 로그인 중 public key 생성에 문제가 발생했습니다.")
        }
    }
}
