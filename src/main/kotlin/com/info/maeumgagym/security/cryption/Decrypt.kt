package com.info.maeumgagym.security.cryption

import com.info.maeumgagym.security.cryption.type.Cryptography

interface Decrypt {

    fun decrypt(encrypted: String, key: String, algorithm: Cryptography): String

    fun decrypt(encrypted: String, key: String, algorithm: String): String
}
