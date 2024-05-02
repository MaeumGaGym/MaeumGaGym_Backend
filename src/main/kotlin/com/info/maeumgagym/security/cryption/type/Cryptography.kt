package com.info.maeumgagym.security.cryption.type

enum class Cryptography(
    val value: String,
) {
    HS256("HS256");

    override fun toString(): String = this.value
}
