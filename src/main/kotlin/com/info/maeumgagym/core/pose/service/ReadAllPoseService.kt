package com.info.maeumgagym.core.pose.service

import com.info.maeumgagym.common.annotation.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.common.exception.MaeumGaGymException
import com.info.maeumgagym.core.pose.dto.response.PoseListResponse
import com.info.maeumgagym.core.pose.port.`in`.ReadAllPoseUseCase
import com.info.maeumgagym.core.pose.port.out.ReadPosePort
import java.time.LocalDateTime

@ReadOnlyUseCase
internal class ReadAllPoseService(
    private val readPosePort: ReadPosePort
) : ReadAllPoseUseCase {

    override fun readAll(lastUpdated: LocalDateTime?): PoseListResponse {
        // 클라이언트의 자세 정보가 최신인지를 판단하는 로직
        // 클라이언트의 마지막 조회 시점이 서버의 마지막 변경 시점보다 이후인 경우 No Content 반환
        // => 클라이언트의 정보가 가장 최신인 경우
        // 클라이언트가 한 번도 조회한 적 없는 경우 정보 반환 (lastUpdated == null)
        if (lastUpdated?.isAfter(readPosePort.getLastModifiedAt()) ?: false) {
            throw MaeumGaGymException.NO_CONTENT
        }

        return PoseListResponse(
            readPosePort.readAll().map {
                it.toInfoResponse()
            }
        )
    }
}
