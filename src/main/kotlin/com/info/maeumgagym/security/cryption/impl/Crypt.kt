package com.info.maeumgagym.security.cryption.impl

import com.info.maeumgagym.security.cryption.Decrypt
import com.info.maeumgagym.security.cryption.Encrypt
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Component
class Crypt : Encrypt, Decrypt {

    private companion object {
        const val DEFAULT_CRYPTO_ALGORITHM = "AES"
    }

    override fun encrypt(unencrypted: String, key: String): String {
        val cipher = getCipher(Cipher.ENCRYPT_MODE, key)

        val encrypted = cipher.doFinal(unencrypted.encodeToByteArray())

        return Base64.getEncoder().encodeToString(
            encrypted
        )
    }

    override fun decrypt(encrypted: String, key: String): String {
        val cipher = getCipher(Cipher.DECRYPT_MODE, key)

        val base64Decoded = Base64.getDecoder().decode(encrypted)

        val decrypted = cipher.doFinal(base64Decoded)

        return decrypted.decodeToString()
    }

    private fun getCipher(mode: Int, key: String): Cipher {
        val cipher = Cipher.getInstance(DEFAULT_CRYPTO_ALGORITHM)

        val spec = SecretKeySpec(key.encodeToByteArray(), DEFAULT_CRYPTO_ALGORITHM)

        cipher.init(mode, spec)

        return cipher
    }
}
