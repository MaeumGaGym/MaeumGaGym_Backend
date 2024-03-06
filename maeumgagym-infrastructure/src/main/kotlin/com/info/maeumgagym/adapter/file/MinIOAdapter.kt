package com.info.maeumgagym.adapter.file

import com.info.maeumgagym.daily.port.out.DeleteImageObjectPort
import com.info.maeumgagym.daily.port.out.GetPreSignedImageUploadURL
import com.info.maeumgagym.global.env.file.MinIOProperties
import io.minio.*
import io.minio.http.Method
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream

@Component
internal class MinIOAdapter(
    private val property: MinIOProperties
) : GetPreSignedImageUploadURL, DeleteImageObjectPort {

    private companion object val minioClient: MinioClient = MinioClient.builder()
        .endpoint(property.endPoint)
        .credentials(property.accessKey, property.secretKey)
        .build()

    override fun getPreSignedUrl(folderName: String, objectName: String): String =
        minioClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.PUT)
                .bucket(property.bucketName)
                .`object`(folderName + objectName)
                .expiry(60 * 5)
                .build()
        )

    fun upload(folderName: String, objectName: String, fileContent: ByteArray) {
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(property.bucketName)
                .`object`(folderName + objectName)
                .stream(ByteArrayInputStream(fileContent), fileContent.size.toLong(), 5242880)
                .build()
        )
    }

    override fun deleteObjects(objectName: String) {
        minioClient.removeObject(
            RemoveObjectArgs.builder()
                .bucket(property.bucketName)
                .`object`(objectName)
                .build()
        )
    }
}
