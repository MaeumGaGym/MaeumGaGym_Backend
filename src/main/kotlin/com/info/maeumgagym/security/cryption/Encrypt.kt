package com.info.maeumgagym.security.cryption

/**
 * 평문 문자열을  AES로 암호화 및 Base64로 인코딩해 반환하는 암호화 클래스
 *
 * @see Decrypt 복호화 클래스
 *
 * @author Daybreak312
 * @since 02-05-2024
 */
interface Encrypt {

    fun encrypt(unencrypted: String, key: String): String
}
