package com.info.maeumgagym.auth.port.out

interface AppleJwtParsePort {

    fun parseHeaders(token: String): MutableMap<String?, String?>
}
