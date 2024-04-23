package com.info.maeumgagym.core.pickle.port.out

import com.info.maeumgagym.core.pickle.model.VideoIdAndUploaderId

interface SaveVideoIdAndUploaderIdPort {
    fun save(videoIdAndUploaderId: VideoIdAndUploaderId): VideoIdAndUploaderId
}
