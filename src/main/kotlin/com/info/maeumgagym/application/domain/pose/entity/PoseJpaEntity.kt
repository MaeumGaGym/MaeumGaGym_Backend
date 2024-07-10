package com.info.maeumgagym.application.domain.pose.entity

import com.info.maeumgagym.application.TableNames
import com.info.maeumgagym.application.converter.StringAttributeConverter
import com.info.maeumgagym.application.domain.base.BaseLongIdEntity
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity

@Entity(name = TableNames.POSE_TABLE)
class PoseJpaEntity(
    needMachine: Boolean,
    isWeightExercise: Boolean,
    category: MutableSet<String>,
    simpleName: String,
    exactName: String,
    thumbnail: String,
    video: String,
    easyPart: MutableSet<String>,
    exactPart: MutableSet<String>,
    startPose: MutableSet<String>,
    exerciseWay: MutableSet<String>,
    breatheWay: MutableSet<String>?,
    caution: MutableSet<String>?,
    id: Long? = null
) : BaseLongIdEntity(id) {

    @Column(name = "need_machine", updatable = true, nullable = false)
    var needMachine: Boolean = needMachine // 기구 운동인지 여부
        protected set

    @Column(name = "is_weight_exercise", updatable = true, nullable = false)
    var isWeightExercise: Boolean = isWeightExercise
        protected set

    @Convert(converter = StringAttributeConverter::class)
    @Column(name = "category", updatable = true, nullable = false)
    var category: MutableSet<String> = category
        protected set

    @Column(name = "simple_name", length = 30, updatable = true, nullable = false)
    var simpleName: String = simpleName // 간단한 이름
        protected set

    @Column(name = "exact_name", length = 60, updatable = true, nullable = false)
    var exactName: String = exactName // 정확한 이름
        protected set

    @Column(name = "thumbnail", length = 1000, updatable = true, nullable = false)
    var thumbnail: String = thumbnail // 썸네일
        protected set

    @Column(name = "video_url", length = 1000, updatable = true, nullable = true)
    var video: String = video // 자세 영상
        protected set

    @Convert(converter = StringAttributeConverter::class)
    @Column(name = "simple_part", length = 100, updatable = true, nullable = false)
    var simplePart: MutableSet<String> = easyPart // 간단한 자극 부위 이름
        protected set

    @Convert(converter = StringAttributeConverter::class)
    @Column(name = "exact_part", length = 100, updatable = true, nullable = false)
    var exactPart: MutableSet<String> = exactPart // 정확한 자극 부위 이름
        protected set

    @Convert(converter = StringAttributeConverter::class)
    @Column(name = "start_pose", length = 1000, updatable = true, nullable = false)
    var startPose: MutableSet<String> = startPose // 시작 자세
        protected set

    @Convert(converter = StringAttributeConverter::class)
    @Column(name = "exercise_way", length = 1500, updatable = true, nullable = false)
    var exerciseWay: MutableSet<String> = exerciseWay // 운동 방법
        protected set

    @Convert(converter = StringAttributeConverter::class)
    @Column(name = "breathe_way", length = 100, updatable = true, nullable = false)
    var breatheWay: MutableSet<String>? = breatheWay // 호흡법
        protected set

    @Convert(converter = StringAttributeConverter::class)
    @Column(name = "caution", updatable = true, nullable = false)
    var caution: MutableSet<String>? = caution // 주의사항
        protected set
}
