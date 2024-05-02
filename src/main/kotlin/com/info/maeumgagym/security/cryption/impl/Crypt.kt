package com.info.maeumgagym.security.cryption.impl

import com.info.maeumgagym.security.cryption.Decrypt
import com.info.maeumgagym.security.cryption.Encrypt
import com.info.maeumgagym.security.cryption.type.Cryptography
import org.springframework.stereotype.Component
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

@Component
class Crypt : Encrypt, Decrypt {

    override fun encrypt(unencrypted: String, key: String, algorithm: Cryptography): String =
        encrypt(unencrypted, key, algorithm.toString())

    override fun encrypt(unencrypted: String, key: String, algorithm: String): String {
        val cipher = getCipher(Cipher.ENCRYPT_MODE, key, algorithm)

        return cipher.doFinal(unencrypted.encodeToByteArray()).toString()
    }

    override fun decrypt(encrypted: String, key: String, algorithm: Cryptography): String =
        decrypt(encrypted, key, algorithm.toString())

    override fun decrypt(encrypted: String, key: String, algorithm: String): String {
        val cipher = getCipher(Cipher.DECRYPT_MODE, key, algorithm)

        return cipher.doFinal(encrypted.encodeToByteArray()).toString()
    }

    private fun getCipher(mode: Int, key: String, algorithm: String): Cipher {
        val cipher = Cipher.getInstance(algorithm)

        val spec = SecretKeySpec(key.encodeToByteArray(), algorithm)

        cipher.init(mode, spec)

        return cipher
    }
}
