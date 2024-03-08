package com.info.maeumgagym.fileserver.minio.config

import com.info.maeumgagym.fileserver.env.MinIOProperties
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
