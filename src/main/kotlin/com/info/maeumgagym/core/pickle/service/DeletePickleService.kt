package com.info.maeumgagym.core.pickle.service

import com.info.maeumgagym.common.annotation.responsibility.UseCase
import com.info.maeumgagym.core.auth.port.out.ReadCurrentUserPort
import com.info.maeumgagym.common.exception.BusinessLogicException
import com.info.maeumgagym.common.exception.SecurityException
import com.info.maeumgagym.core.pickle.port.`in`.DeletePickleUseCase
import com.info.maeumgagym.core.pickle.port.out.DeleteOriginalVideoPort
import com.info.maeumgagym.core.pickle.port.out.DeletePicklePort
import com.info.maeumgagym.core.pickle.port.out.ReadPicklePort

@UseCase
internal class DeletePickleService(
    private val deletePicklePort: DeletePicklePort,
    private val deleteOriginalVideoPort: DeleteOriginalVideoPort,
    private val readCurrentUserPort: ReadCurrentUserPort,
    private val readPicklePort: ReadPicklePort
) : DeletePickleUseCase {

    override fun deleteFromId(id: String) {
        // 토큰으로 유저 불러오기
        val user = readCurrentUserPort.readCurrentUser()

        // 넘겨 받은 파라미터로 피클 찾기, 없다면 -> 예외처리
        val pickle = readPicklePort.readById(id) ?: throw BusinessLogicException.PICKLE_NOT_FOUND

        // 업로더가 유저와 일치하는지 확인, 아닐시 -> 권한 에러
        if (pickle.uploader.id != user.id) throw SecurityException.PERMISSION_DENIED

        // 피클 삭제
        deletePicklePort.delete(pickle)

        // 피클 파일 삭제
        deleteOriginalVideoPort.callDeleteAPIOnExternal(pickle.videoId)
    }
}
