package com.info.maeumgagym.security.cryption

/**
 * Base64로 인코딩된 AES 암호화문을 복호화하는 클래스
 *
 * @see Encrypt 암호화 클래스
 *
 * @author Daybreak312
 * @since 02-05-2024
 */
interface Decrypt {

    fun decrypt(encrypted: String, key: String): String
}
