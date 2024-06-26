package com.info.maeumgagym.infrastructure.external.file.minio.config

import com.info.maeumgagym.infrastructure.env.file.MinIOProperties
import io.minio.MinioClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MinIOConfig(
    private val property: MinIOProperties
) {

    @Bean
    fun minioClient(): MinioClient = MinioClient.builder()
        .endpoint(property.endPoint)
        .credentials(property.accessKey, property.secretKey)
        .build()
}
