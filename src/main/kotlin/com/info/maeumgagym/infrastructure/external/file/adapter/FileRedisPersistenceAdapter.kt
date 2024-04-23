package com.info.maeumgagym.infrastructure.external.file.adapter

import com.info.maeumgagym.core.pickle.model.VideoIdAndUploaderId
import com.info.maeumgagym.core.pickle.port.out.ReadVideoIdAndUploaderIdPort
import com.info.maeumgagym.core.pickle.port.out.SaveVideoIdAndUploaderIdPort
import com.info.maeumgagym.infrastructure.env.file.FileProperties
import com.info.maeumgagym.infrastructure.external.file.VideoIdAndUploaderIdRepository
import com.info.maeumgagym.infrastructure.external.file.entity.VideoIdAndUploaderIdRedisEntity
import org.springframework.stereotype.Component

@Component
internal class FileRedisPersistenceAdapter(
    private val videoIdAndUploaderIdRepository: VideoIdAndUploaderIdRepository,
    private val fileProperties: FileProperties
) : SaveVideoIdAndUploaderIdPort, ReadVideoIdAndUploaderIdPort {

    override fun save(videoIdAndUploaderId: VideoIdAndUploaderId): VideoIdAndUploaderId =
        videoIdAndUploaderIdRepository.save(
            VideoIdAndUploaderIdRedisEntity(
                videoIdAndUploaderId.videoId,
                videoIdAndUploaderId.uploaderId,
                fileProperties.videoIdSaveTtl
            )
        ).run { VideoIdAndUploaderId(videoId, uploaderId) }

    override fun readByVideoId(videoId: String): VideoIdAndUploaderId? =
        videoIdAndUploaderIdRepository.findById(videoId)?.let {
            VideoIdAndUploaderId(it.videoId, it.uploaderId)
        }
}
