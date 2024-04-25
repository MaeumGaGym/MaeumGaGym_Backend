package com.info.maeumgagym.core.pickle.port.out

interface ExistsPicklePort {
    fun existsById(videoId: String): Boolean
}
