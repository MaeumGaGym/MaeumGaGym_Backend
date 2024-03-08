package com.info.maeumgagym.fileserver

import com.info.maeumgagym.fileserver.entity.VideoIdAndUploaderIdRedisEntity
import org.springframework.data.repository.Repository

interface VideoIdAndUploaderIdRepository : Repository<VideoIdAndUploaderIdRedisEntity, String> {

    fun save(videoIdAndUploaderIdRedisEntity: VideoIdAndUploaderIdRedisEntity): VideoIdAndUploaderIdRedisEntity

    fun findById(id: String): VideoIdAndUploaderIdRedisEntity?
}
