package com.info.maeumgagym.common.exception

import com.info.common.UseCase
import com.info.maeumgagym.common.enum.DomainNames

/**
 * 마음가짐 내에서 전역적으로 쓰이는 예외 클래스의 최상위 타입
 *
 * 이 타입으로 예외를 발생시키기보단, 하위 클래스 사용을 권고
 *
 * | Daybreak312
 *
 * 이전의 Exception 사용 방식은 다음과 같았습니다;
 *
 * 1. 한 최상위 abstract class(MaeumGaGymException)와,
 * 2. 하위 object class,
 * 3. 그 object class의 본질인 Status Code와 Message가 담긴 enum 클래스.
 *
 * 이 세 개만을 이용해 예외를 마치 enum처럼 처리했습니다.
 * ```
 * abstract class MaeumGaGymException(
 *     val errorCode: ErrorCode
 * ) : RuntimeException()
 *
 * enum class ErrorCode(
 *     val status: Int,
 *     val message: String
 * ) {
 *     PICKLE_NOT_FOUND(404, "Pickle Not Found");
 * }
 *
 * object PickleNotFoundException : MaeumGaGymException(ErrorCode.PICKLE_NOT_FOUND)
 * ```
 * 하지만 이러한 방식은 하나의 예외를 만들 때마다 ErrorCode를 추가하고, object를 추가하고, 이것을 throw하는 것까지 불필요하게 복잡한 방식으로 만들어야 하는 것에 불편을 느꼈습니다.
 *
 * 그래서 저는,
 * 1. 예외를 발생시키는 자리에서 입력 가능한 [message]로 보다 편하게 예외를 발생시킬 수 있게 하고,
 * 2. 최상위 타입 [MaeumGaGymException]의 *static field*로 이전의 싱글톤 예외 방식의 이점을 확보했으며,
 * 3. 다형성을 이용한 Exception 발생 위치 기록의 편리성을 챙겼습니다.
 *
 * | Daybreak312
 *
 * @param status Http 표준 상태 코드
 * @param message 해당 예외 발생의 설명
 *
 * @see DomainException
 * @see FilterException
 * @see InterceptorException
 * @see SecurityException
 * @see CriticalException
 */
open class MaeumGaGymException(
    val status: Int,
    message: String
) : RuntimeException(message) {

    companion object {

        // Feign
        val FEIGN_BAD_REQUEST = DomainException(400, "Feign Bad Request")
        val FEIGN_UNAUTHORIZED = DomainException(401, "Feign UnAuthorized")
        val FEIGN_FORBIDDEN = DomainException(403, "Feign Forbidden")
        val FEIGN_SERVER_ERROR = DomainException(500, "Feign Server Error")

        // No Content
        val THERE_NO_PICKLES = DomainException(204, "업로드된 피클이 존재하지 않습니다.")

        // Internal Server Error
        val INTERNAL_SERVER_ERROR = MaeumGaGymException(500, "Internal Server Error")
        val ROLE_CONVERTER_ERROR = CriticalException(500, "Role Converter Error")

        // UnAuthorized
        val INVALID_TOKEN = SecurityException(401, "Invalid Token")
        val EXPIRED_TOKEN = SecurityException(401, "Expired Token")
        val UNAUTHORIZED = SecurityException(401, "Un Authorized")

        // Forbidden
        val PERMISSION_DENIED = DomainException(403, "Permission Denied")
        val VIDEO_AND_UPLOADER_MISMATCHED = DomainException(403, "Video And Uploader Mismatched")

        // Not Found
        val USER_NOT_FOUND = DomainException(404, "User Not Found")
        val POSE_NOT_FOUND = DomainException(404, "Pose Not Found")
        val ROUTINE_NOT_FOUND = DomainException(404, "Routine Not Found")
        val PICKLE_NOT_FOUND = DomainException(404, "Pickle Not Found")
        val PICKLE_COMMENT_NOT_FOUND = DomainException(404, "Pickle Comment Not Found")
        val PICKLE_REPLY_NOT_FOUND = DomainException(404, "Pickle Reply Not Found")
        val STEP_NOT_FOUND = DomainException(404, "Step Not Found")

        // Bad Request
        val FILE_TYPE_MISMATCHED = DomainException(400, "File Type Mismatched")
        val EXERCISE_LIST_CANNOT_EMPTY = DomainException(400, "Exercise list cannot empty")
        val PICKLE_MISMATCHED = DomainException(400, "Pickle Mismatched")
        val WAKA_STARTED_NOT_YET = DomainException(400, "Wakatime Started Not Yet")
        val CANNNOT_REPORT_ONESELF = DomainException(400, "Cannot Report Oneself")
        val NOT_UPLOADED_TO_VIDEO_SERVER = DomainException(400, "Does Not Uploaded In Video Server")
        val TAG_TOO_LONG = DomainException(400, "Tag Too Long, Tag Cannot Longer than 10")
        val START_DATE_CANNOT_AFTER_THAN_END_TIME = DomainException(400, "Start Date Cannot After than End Time.")

        // Conflict
        val DUPLICATED_NICKNAME = DomainException(409, "Duplicated Nickname")
        val ALREADY_EXIST_USER = DomainException(409, "Already Exists User")
        val ALREADY_EXIST_PICKLE = DomainException(409, "Already Exists Pickle")
        val ALREADY_EXIST_STEP = DomainException(409, "Alreay Exists Step")
        val ALREADY_STARTED_WAKA = DomainException(409, "Already Started Wakatime")
        val OTHER_ROUTINE_ALREADY_USING_AT_DAY_OF_WEEK =
            DomainException(409, "Other Routine Already Using At Day of week")
    }
}

/**
 * 도메인 로직 실행 중에 발생한 예외를 위한 클래스
 *
 * @see MaeumGaGymException
 * @see UseCase
 */
class DomainException(
    status: Int,
    message: String
) : MaeumGaGymException(status, message) {

    /**
     * [message] 표준화를 위해 도메인 이름과 Http 상태 코드 메세지를 이용해 [message]를 작성하는 생성자
     * Sample: "찾을 수 없는 피클" 예외 발생 상황
     *
     * Pickle([domainName]) + Not Found([ErrorCodeSuffix]) -> Pickle Not Found([message])
     *
     *
     * @param status 기본 생성자의 [status]
     * @param domainName 도메인 이름; [status]를 기준으로 [domainName] + Http Status Message를 합쳐 기본 생성자의 [message]로 삽입
     *
     *
     * @see ErrorCodeSuffix
     */
    constructor(status: Int, domainName: DomainNames) :
        this(
            status = status,
            message = domainName.value + (getErrorCodeSuffixWithStatus(status)?.message
                ?: throw MaeumGaGymException.INTERNAL_SERVER_ERROR as CriticalException)
        )
}

/**
 * 예외 발생 시에 [message] 표준화를 위해 사용하는 에러 코드 접미사
 *
 * Sample: "찾을 수 없는 피클" 예외 발생 상황
 *
 * [DomainException]
 * (400,
 * [DomainNames.PICKLE])
 *
 * @see DomainException
 */
enum class ErrorCodeSuffix(
    val status: Int,
    val message: String
) {
    NOT_FOUND(400, " Not Found")
}

private fun getErrorCodeSuffixWithStatus(status: Int): ErrorCodeSuffix? {
    ErrorCodeSuffix.values().forEach {
        if (it.status == status) return it
    }
    return null
}

/**
 * 필터 실행 중에 발생한 예외
 *
 * @see MaeumGaGymException
 */
class FilterException(
    status: Int,
    message: String
) : MaeumGaGymException(status, message)

/**
 * 인터셉터 실행 중에 발생한 예외
 *
 * @see MaeumGaGymException
 */
class InterceptorException(
    status: Int,
    message: String
) : MaeumGaGymException(status, message)

/**
 * 컨트롤러에서 유효성 검증 과정에서 발생한 예외
 *
 * [ErrorLogResponseFilter]에서 Validation 실패시 발생하는 예외들을 이 예외로 변환 후 처리
 *
 * @see MaeumGaGymException
 */
class PresentationValidationException(
    status: Int,
    message: String,
    val fields: Map<String, String>
) : MaeumGaGymException(status, message)

/**
 * SecurityFilterChain 실행 중에 발생한 예외
 *
 * @see MaeumGaGymException
 */
class SecurityException(
    status: Int,
    message: String
) : MaeumGaGymException(status, message)

/**
 * 서버에 타격을 줄 수 있는 예외
 *
 * @see MaeumGaGymException
 */
class CriticalException(
    status: Int,
    message: String
) : MaeumGaGymException(status, message)
