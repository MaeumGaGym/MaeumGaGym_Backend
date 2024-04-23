package com.info.maeumgagym.core.auth.port.out

interface AppleJwtParsePort {

    fun parseHeaders(token: String): MutableMap<String?, String?>
}
