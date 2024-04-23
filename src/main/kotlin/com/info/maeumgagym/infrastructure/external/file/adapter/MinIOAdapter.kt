package com.info.maeumgagym.external.file.adapter

import com.info.maeumgagym.core.daily.port.out.DeleteImageObjectPort
import com.info.maeumgagym.core.daily.port.out.GetPreSignedImageUploadURL
import com.info.maeumgagym.env.file.MinIOProperties
import io.minio.GetPresignedObjectUrlArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import io.minio.RemoveObjectArgs
import io.minio.http.Method
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.time.LocalDate

@Component
internal class MinIOAdapter(
    private val property: MinIOProperties,
    private val minioClient: MinioClient
) : com.info.maeumgagym.core.daily.port.out.GetPreSignedImageUploadURL,
    com.info.maeumgagym.core.daily.port.out.DeleteImageObjectPort {

    private companion object {
        const val FILE_PATH_FORMAT = "daily_exercise_complete/%s/%s/%s"
    }

    override fun getPreSignedUrl(oauthId: String, objectName: String): String =
        minioClient.getPresignedObjectUrl(
            GetPresignedObjectUrlArgs.builder()
                .method(Method.PUT)
                .bucket(property.bucketName)
                .`object`(FILE_PATH_FORMAT.format(oauthId, LocalDate.now(), objectName))
                .expiry(60 * 5)
                .build()
        )

    /**
     * Version: 1.3.0 Alpha
     * @since 06.03.2024
     */
    @Deprecated("파일 I/O비용 문제로 pre-signed로 변경된", level = DeprecationLevel.HIDDEN)
    private fun upload(folderName: String, objectName: String, fileContent: ByteArray) {
        minioClient.putObject(
            PutObjectArgs.builder()
                .bucket(property.bucketName)
                .`object`(folderName + objectName)
                .stream(ByteArrayInputStream(fileContent), fileContent.size.toLong(), 5242880)
                .build()
        )
    }

    override fun deleteObjects(oauthId: String, date: LocalDate, objectName: String) {
        minioClient.removeObject(
            RemoveObjectArgs.builder()
                .bucket(property.bucketName)
                .`object`(FILE_PATH_FORMAT.format(oauthId, LocalDate.now(), objectName))
                .build()
        )
    }
}
