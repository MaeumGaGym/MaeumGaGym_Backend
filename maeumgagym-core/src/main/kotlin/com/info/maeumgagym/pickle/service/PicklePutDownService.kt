package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.auth.exception.PermissionDeniedException
import com.info.maeumgagym.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.pickle.exception.PickleNotFoundException
import com.info.maeumgagym.pickle.port.`in`.PicklePutDownUseCase
import com.info.maeumgagym.pickle.port.out.DeletePicklePort
import com.info.maeumgagym.pickle.port.out.ReadPickleByIdPort

@UseCase
class PicklePutDownService(
    private val deletePicklePort: DeletePicklePort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readPickleByIdPort: ReadPickleByIdPort
) : PicklePutDownUseCase {

    override fun putDownPickleById(id: Long) {
        val user = readCurrentUserPort.readCurrentUser()

        val pickle = readPickleByIdPort.readPickleById(id)
            ?: throw PickleNotFoundException

        if (pickle.uploader.id != user.id) throw PermissionDeniedException

        deletePicklePort.deletePickle(pickle)
    }
}
