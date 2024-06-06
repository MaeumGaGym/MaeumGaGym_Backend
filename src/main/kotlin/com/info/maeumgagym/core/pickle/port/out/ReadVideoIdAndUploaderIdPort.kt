package com.info.maeumgagym.core.pickle.port.out

import com.info.maeumgagym.core.pickle.model.VideoIdAndUploaderId

interface ReadVideoIdAndUploaderIdPort {
    fun readByVideoId(videoId: String): VideoIdAndUploaderId?
}
