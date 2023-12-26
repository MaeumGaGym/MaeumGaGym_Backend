package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.pickle.dto.request.PickleUploadRequest
import com.info.maeumgagym.pickle.model.Pickle
import com.info.maeumgagym.pickle.port.`in`.PickleUploadUseCase
import com.info.maeumgagym.pickle.port.out.SavePicklePort

@UseCase
class UploadPickleService(
    private val savePicklePort: SavePicklePort,
    private val readCurrentUserPort: ReadCurrentUserPort
) : PickleUploadUseCase {

    override fun uploadPickle(req: PickleUploadRequest) {
        val user = readCurrentUserPort.readCurrentUser()

        savePicklePort.savePickle(
            Pickle(
                title = req.title,
                description = req.description,
                uploader = user,
                videoUrl = req.videoUrl,
                tags = req.tags
            )
        )
    }
}
