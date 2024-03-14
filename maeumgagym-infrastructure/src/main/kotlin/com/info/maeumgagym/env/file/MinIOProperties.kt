package com.info.maeumgagym.env.file

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("minio")
data class MinIOProperties(
    val endPoint: String,
    val accessKey: String,
    val secretKey: String,
    val bucketName: String
)
