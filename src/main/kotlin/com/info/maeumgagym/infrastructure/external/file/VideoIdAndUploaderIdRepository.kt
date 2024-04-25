package com.info.maeumgagym.infrastructure.external.file

import com.info.maeumgagym.infrastructure.external.file.entity.VideoIdAndUploaderIdRedisEntity
import org.springframework.data.repository.Repository

interface VideoIdAndUploaderIdRepository : Repository<VideoIdAndUploaderIdRedisEntity, String> {

    fun save(videoIdAndUploaderIdRedisEntity: VideoIdAndUploaderIdRedisEntity): VideoIdAndUploaderIdRedisEntity

    fun findById(id: String): VideoIdAndUploaderIdRedisEntity?
}
