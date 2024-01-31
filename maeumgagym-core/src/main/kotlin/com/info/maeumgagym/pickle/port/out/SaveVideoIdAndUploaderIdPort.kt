package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.VideoIdAndUploaderId

interface SaveVideoIdAndUploaderIdPort {
    fun save(videoIdAndUploaderId: VideoIdAndUploaderId): VideoIdAndUploaderId
}
