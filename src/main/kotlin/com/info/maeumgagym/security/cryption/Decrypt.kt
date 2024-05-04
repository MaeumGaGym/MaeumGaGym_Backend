package com.info.maeumgagym.security.cryption

interface Decrypt {

    fun decrypt(encrypted: ByteArray, key: String): ByteArray
}
