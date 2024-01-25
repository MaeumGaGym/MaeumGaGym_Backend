package com.info.maeumgagym.pickle.port.`in`

import com.info.maeumgagym.pickle.dto.request.PickleUploadRequest

interface CreatePickleUseCase {

    fun createPickle(req: PickleUploadRequest)
}
