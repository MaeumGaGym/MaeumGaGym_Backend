package com.info.maeumgagym.security.cryption

import com.info.maeumgagym.security.cryption.type.Cryptography

interface Encrypt {

    fun encrypt(unencrypted: String, key: String, algorithm: Cryptography): String

    fun encrypt(unencrypted: String, key: String, algorithm: String): String
}
