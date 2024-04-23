package com.info.maeumgagym.infrastructure.env.file

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("file")
data class FileProperties(

    val secretKey: String,

    val serverURL: String,

    val suffixPath: String,

    val videoIdSaveTtl: Long
)
