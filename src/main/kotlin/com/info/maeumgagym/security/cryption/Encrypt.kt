package com.info.maeumgagym.security.cryption

interface Encrypt {

    fun encrypt(unencrypted: ByteArray, key: String): ByteArray
}
