package com.info.maeumgagym.pickle.service

import com.info.common.UseCase
import com.info.maeumgagym.pickle.dto.response.PickleListResponse
import com.info.maeumgagym.pickle.dto.response.PickleResponse
import com.info.maeumgagym.pickle.exception.PickleNotFoundException
import com.info.maeumgagym.pickle.exception.ThereNoPicklesException
import com.info.maeumgagym.pickle.model.Pickle
import com.info.maeumgagym.pickle.model.Pickle.Companion.toResponse
import com.info.maeumgagym.pickle.port.`in`.LoadPickleFromIdUseCase
import com.info.maeumgagym.pickle.port.`in`.LoadPicklesFromPoseUseCase
import com.info.maeumgagym.pickle.port.`in`.LoadRecommendationPicklesUseCase
import com.info.maeumgagym.pickle.port.out.GenerateUploadURLPort
import com.info.maeumgagym.pickle.port.out.ReadAllPicklesPort
import com.info.maeumgagym.pickle.port.out.ReadPickleByIdPort
import com.info.maeumgagym.pose.exception.PoseNotFoundException
import com.info.maeumgagym.pose.port.out.FindPoseByIdPort

@UseCase
class LoadPickleService(
    private val readAllPicklesPort: ReadAllPicklesPort,
    private val readPickleByIdPort: ReadPickleByIdPort,
    private val generateUploadURLPort: GenerateUploadURLPort,
    private val readPoseByIdPort: FindPoseByIdPort
) : LoadRecommendationPicklesUseCase, LoadPickleFromIdUseCase, LoadPicklesFromPoseUseCase {

    private companion object {
        const val INDEX_SIZE = 5
    }

    override fun loadRecommendationPickles(index: Int): PickleListResponse {
        // 모든 피클 불러오기
        val allPickles = readAllPicklesPort.readAllPickles()

        // 모든 피클의 개수가 현재 클라이언트가 이미 불러온 피클의 개수보다 적다면 - > 더이상 피클이 없다는 예외 처리
        if (allPickles.isEmpty() || allPickles.size <= index * INDEX_SIZE) throw ThereNoPicklesException

        // dto로 변환
        return PickleListResponse(
            getRandomPickles(allPickles).map { it.toResponse(generateUploadURLPort.generateURL(it.videoId)) }
        )
    }

    override fun loadPickleFromId(id: String): PickleResponse =
        // (id = 파라미터)인 피클이 존재한다면 -> response, else -> 예외처리
        (readPickleByIdPort.readPickleById(id) ?: throw PickleNotFoundException)
            .toResponse(generateUploadURLPort.generateURL(id))

    override fun loadPicklesFromPose(poseId: Long): PickleListResponse {
        // id로 자세 불러오기
        val pose = readPoseByIdPort.findById(poseId) ?: throw PoseNotFoundException

        // 모든 피클 불러오기
        val allPickles = readAllPicklesPort.readAllPickles()
        // 자세와 관련된 피클을 담을 빈 리스트
        val pickles: MutableList<Pickle> = mutableListOf()
        // 모든 피클 중에서
        allPickles.map {
            // 만약 선택된 자세의 이름과 같은 태그가 있다면 ->
            if (it.tags.contains(pose.simpleName) || it.tags.contains(pose.exactName)) {
                // 리스트에 추가
                pickles.add(it)
            }
        }

        // dto로 변환
        return PickleListResponse(
            getRandomPickles(pickles).map { it.toResponse(generateUploadURLPort.generateURL(it.videoId)) }
        )
    }

    private fun getRandomPickles(pickles: List<Pickle>): List<Pickle> =
        // 만약 랜덤 피클을 추출할 베이스 리스트의 크기가 추출할 크기보다 작다면 -> 베이스 리스트 그대로 반환
        pickles.takeIf { pickles.toSet().size <= INDEX_SIZE }
            // 아니라면 빈 Set을 생성해서 (중복 제거)
            ?: mutableSetOf<Pickle>().apply {
                // 총 개수가 INDEX_SIZE와 같아질 때까지 랜덤 피클 추가
                while (size < INDEX_SIZE) add(pickles.random())
            }.toList()
}
