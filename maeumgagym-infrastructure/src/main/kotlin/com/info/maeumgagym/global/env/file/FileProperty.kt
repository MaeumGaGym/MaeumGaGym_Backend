package com.info.maeumgagym.global.env.file

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("file")
data class FileProperty(

    val secretKey: String
)
