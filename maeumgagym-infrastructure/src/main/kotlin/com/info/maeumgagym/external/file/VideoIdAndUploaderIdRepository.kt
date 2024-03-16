package com.info.maeumgagym.external.file

import com.info.maeumgagym.external.file.entity.VideoIdAndUploaderIdRedisEntity
import org.springframework.data.repository.Repository

interface VideoIdAndUploaderIdRepository : Repository<VideoIdAndUploaderIdRedisEntity, String> {

    fun save(videoIdAndUploaderIdRedisEntity: VideoIdAndUploaderIdRedisEntity): VideoIdAndUploaderIdRedisEntity

    fun findById(id: String): VideoIdAndUploaderIdRedisEntity?
}
