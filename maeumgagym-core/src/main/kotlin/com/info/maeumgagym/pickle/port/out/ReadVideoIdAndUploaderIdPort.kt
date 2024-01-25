package com.info.maeumgagym.pickle.port.out

import com.info.maeumgagym.pickle.model.VideoIdAndUploaderId

interface ReadVideoIdAndUploaderIdPort {
    fun readByVideoId(videoId: String): VideoIdAndUploaderId?
}
