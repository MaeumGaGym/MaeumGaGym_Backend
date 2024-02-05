package com.info.maeumgagym.pickle.service

import com.info.common.ReadOnlyUseCase
import com.info.maeumgagym.pickle.dto.response.PickleListResponse
import com.info.maeumgagym.pickle.dto.response.PickleResponse
import com.info.maeumgagym.pickle.exception.PickleNotFoundException
import com.info.maeumgagym.pickle.exception.ThereNoPicklesException
import com.info.maeumgagym.pickle.model.Pickle
import com.info.maeumgagym.pickle.model.Pickle.Companion.toResponse
import com.info.maeumgagym.pickle.port.`in`.LoadPickleFromIdUseCase
import com.info.maeumgagym.pickle.port.`in`.LoadPickleFromPoseUseCase
import com.info.maeumgagym.pickle.port.`in`.LoadRecommendationPicklesUseCase
import com.info.maeumgagym.pickle.port.out.GenerateM3u8URLPort
import com.info.maeumgagym.pickle.port.out.ReadPicklePort
import com.info.maeumgagym.pose.exception.PoseNotFoundException
import com.info.maeumgagym.pose.port.out.ReadPosePort

@ReadOnlyUseCase
internal class LoadPickleService(
    private val readPicklePort: ReadPicklePort,
    private val generateM3u8URLPort: GenerateM3u8URLPort,
    private val readPosePort: ReadPosePort
) : LoadRecommendationPicklesUseCase, LoadPickleFromIdUseCase, LoadPickleFromPoseUseCase {

    private companion object {
        const val INDEX_SIZE = 5
    }

    override fun loadRecommendationPickles(index: Int): PickleListResponse {
        // 모든 피클 불러오기
        val allPickles = readPicklePort.readAll()

        // 모든 피클의 개수가 현재 클라이언트가 이미 불러온 피클의 개수보다 적다면 - > 더이상 피클이 없다는 예외 처리
        if (allPickles.isEmpty() || allPickles.size <= index * INDEX_SIZE) throw ThereNoPicklesException

        // dto로 변환
        return PickleListResponse(
            getRandomPickles(allPickles).map { it.toResponse(generateM3u8URLPort.generateURL(it.videoId)) }
        )
    }

    override fun loadPickleFromId(id: String): PickleResponse =
        // (id = 파라미터)인 피클이 존재한다면 -> response, else -> 예외처리
        (readPicklePort.readById(id) ?: throw PickleNotFoundException)
            .toResponse(generateM3u8URLPort.generateURL(id))

    override fun loadAllPagedFromPose(poseId: Long, idx: Int, size: Int): PickleListResponse {
        // id로 자세 불러오기
        val pose = readPosePort.readById(poseId) ?: throw PoseNotFoundException

        val pickles = readPicklePort.readAllPagedByTagsContaining(pose.simpleName, pose.exactName, idx, size)

        // dto로 변환
        return PickleListResponse(
            pickles.map {
                it.toResponse(generateM3u8URLPort.generateURL(it.videoId))
            }
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
