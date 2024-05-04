package com.info.maeumgagym.security.cryption.impl

import com.info.maeumgagym.security.cryption.Decrypt
import com.info.maeumgagym.security.cryption.Encrypt
import org.springframework.stereotype.Component
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Component
class Crypt : Encrypt, Decrypt {

    private companion object {
        const val DEFAULT_CRYPTO_ALGORITHM = "AES"
    }

    override fun encrypt(unencrypted: ByteArray, key: String): ByteArray {
        val cipher = getCipher(Cipher.ENCRYPT_MODE, key)

        return cipher.doFinal(unencrypted)
    }

    override fun decrypt(encrypted: ByteArray, key: String): ByteArray {
        val cipher = getCipher(Cipher.DECRYPT_MODE, key)

        return cipher.doFinal(encrypted)
    }

    private fun getCipher(mode: Int, key: String): Cipher {
        val cipher = Cipher.getInstance(DEFAULT_CRYPTO_ALGORITHM)

        val spec = SecretKeySpec(key.encodeToByteArray(), DEFAULT_CRYPTO_ALGORITHM)

        cipher.init(mode, spec)

        return cipher
    }
}
