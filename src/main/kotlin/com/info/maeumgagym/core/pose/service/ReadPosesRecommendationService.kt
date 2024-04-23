package com.info.maeumgagym.core.pose.service

import com.info.common.responsibility.ReadOnlyUseCase
import com.info.maeumgagym.core.pose.dto.response.PoseListResponse
import com.info.maeumgagym.core.pose.dto.response.PoseRecommendationListResponse
import com.info.maeumgagym.core.pose.model.Pose
import com.info.maeumgagym.core.pose.port.`in`.ReadPosesRecommendationUseCase
import com.info.maeumgagym.core.pose.port.out.ReadPosePort

@ReadOnlyUseCase
class ReadPosesRecommendationService(
    private val readPosePort: ReadPosePort
) : ReadPosesRecommendationUseCase {

    override fun readRecommendation(): PoseRecommendationListResponse {
        // 사용자에게 추천할 자세의 종류와 자세들을 담을 리스트
        // 자세 종류를 Key로, 그 종류에 해당하는 자세들의 List를 Value로 가짐
        // 처음에는 초기화가 되어있지 않으므로, 자세 종류를 이 Map에 추가할 때마다 초기화가 필요함
        val recommendationPoses = mutableMapOf<String, MutableList<Pose>>()

        // 사용자에게 추천할 자세 추출
        readPosePort.readAllRandomLimit30().forEach {
            // 자세의 종류
            val partName = it.simplePart.first()
            // 자세의 종류에 해당하는 자세들의 리스트
            var poses = recommendationPoses[partName]
            // 만약 이번에 처음으로 자세들을 담은 리스트에 접근한다면
            if (poses == null) {
                // 위 주석에서 언급했듯, 새로운 자세 종류이므로 초기화 진행
                recommendationPoses[partName] = mutableListOf()
                poses = recommendationPoses[partName]
            }
            // 자세 리스트에 추가
            poses!!.add(it)
        }

        return PoseRecommendationListResponse(
            recommendationPoses.map {
                Pair(it.key, PoseListResponse(it.value.map { pose -> pose.toInfoResponse() }))
            }.toMap()
        )
    }
}
