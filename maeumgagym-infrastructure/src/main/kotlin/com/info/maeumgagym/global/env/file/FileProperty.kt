package com.info.maeumgagym.global.env.file

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("file")
data class FileProperty(

    val secretKey: String,

    val url1: String,

    val url2: String,

    val suffixPath: String
) {
    val urls: List<String> = listOf(url1, url2)
}
