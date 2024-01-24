package com.info.maeumgagym.pickle.port.out

interface ExistsPicklePort {
    fun existsById(videoId: String): Boolean
}
