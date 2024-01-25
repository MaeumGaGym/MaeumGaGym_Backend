package com.info.maeumgagym.adapter.file

import com.info.maeumgagym.global.env.file.FileProperties
import com.info.maeumgagym.global.file.VideoIdAndUploaderIdRepository
import com.info.maeumgagym.global.file.entity.VideoIdAndUploaderIdRedisEntity
import com.info.maeumgagym.pickle.model.VideoIdAndUploaderId
import com.info.maeumgagym.pickle.port.out.ReadVideoIdAndUploaderIdPort
import com.info.maeumgagym.pickle.port.out.SaveVideoIdAndUploaderIdPort
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
