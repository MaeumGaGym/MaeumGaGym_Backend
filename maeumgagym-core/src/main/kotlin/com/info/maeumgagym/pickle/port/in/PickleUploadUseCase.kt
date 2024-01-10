package com.info.maeumgagym.pickle.port.`in`

import com.info.maeumgagym.pickle.dto.request.PickleUploadRequest

interface PickleUploadUseCase {

    fun uploadPickle(req: PickleUploadRequest)
}
