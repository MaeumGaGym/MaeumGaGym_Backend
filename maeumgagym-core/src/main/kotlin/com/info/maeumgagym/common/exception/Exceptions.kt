package com.info.maeumgagym.common.exception

import com.info.common.UseCase
import com.info.maeumgagym.common.enum.DomainNames

/**
 * 마음가짐 내에서 전역적으로 쓰이는 예외 클래스의 최상위 타입
 *
 * 이 타입으로 예외를 발생시키기보단, 하위 클래스 사용을 권고
 *
 * | Daybreak312 - Exception을 이와 같이 개선한 이유 -
 *
 * 이전의 Exception 사용 방식은 다음과 같았습니다;
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
 * 3. 다형성을 이용한 Exception 형태 기록의 편리성을 챙겼습니다.
 *
 * | Daybreak312
 *
 * @param status Http 표준 상태 코드
 * @param message 해당 예외 발생의 설명
 *
 * @see BusinessLogicException
 * @see FilterException
 * @see InterceptorException
 * @see AuthenticationException
 * @see SecurityException
 * @see FeignException
 * @see CriticalException
 */
open class MaeumGaGymException(
    val status: Int,
    message: String
) : RuntimeException(message) {

    companion object {

        // Default
        val NO_CONTENT get() = MaeumGaGymException(204, "There's No Content Left")
        val BAD_REQUEST get() = MaeumGaGymException(400, "Bad Request")
        val UNAUTHORIZED get() = MaeumGaGymException(401, "Unauthorized")
        val FORBIDDEN get() = MaeumGaGymException(403, "Forbidden")
        val NOT_FOUND get() = MaeumGaGymException(404, "Not Found")
        val CONFLICT get() = MaeumGaGymException(409, "Conflict")
        protected val I_M_A_TEAPOT get() = MaeumGaGymException(418, "I'm a Teapot")
        val INTERNAL_SERVER_ERROR get() = MaeumGaGymException(500, "Internal Server Error")
    }
}

/**
 * 비즈니스 로직 실행 중에 발생한 예외를 위한 클래스
 *
 * @see MaeumGaGymException
 * @see UseCase
 */
class BusinessLogicException(
    status: Int,
    message: String
) : MaeumGaGymException(status, message) {

    /**
     * [message] 표준화를 위해 도메인 이름과 Http 상태 코드 메세지를 이용해 [message]를 작성하는 생성자
     *
     *
     * Sample: "찾을 수 없는 피클" 예외 발생 상황
     *
     * | Pickle([domainName]) + Not Found([ErrorCodePrefixSuffix]) -> Pickle Not Found([message])
     *
     * Sample: "이미 존재하는 유저" 예외 발생 상황
     *
     * | Already Exists([ErrorCodePrefixSuffix]) + User([DomainNames]) -> Already Exists User([message])
     *
     * @param errorCodePrefixSuffix 기본 생성자의 [status]와 [status]를 결정
     * @param domainName 도메인 이름; [status]를 기준으로 [domainName] + Http Status Message를 합쳐 기본 생성자의 [message]로 삽입
     *
     *
     * @see ErrorCodePrefixSuffix
     */
    constructor(errorCodePrefixSuffix: ErrorCodePrefixSuffix, domainName: DomainNames) :
        this(
            status = errorCodePrefixSuffix.status,
            message = if (errorCodePrefixSuffix.isPrefix) {
                errorCodePrefixSuffix.message + domainName.value
            } else {
                domainName.value + errorCodePrefixSuffix.message
            }
        )

    companion object {

        // Bad Request
        val CANNNOT_REPORT_ONESELF get() = BusinessLogicException(400, "Cannot Report Oneself")
        val TAG_TOO_LONG get() = BusinessLogicException(400, "Tag Too Long, Tag Cannot Longer than 10")
        val START_DATE_MUST_BE_BEFORE_THAN_END_DATE
            get() = BusinessLogicException(400, "Start date Must Be Before Than End Date")

        // Not Found
        val USER_NOT_FOUND get() = BusinessLogicException(ErrorCodePrefixSuffix.XXX_NOT_FOUND, DomainNames.USER)
        val POSE_NOT_FOUND get() = BusinessLogicException(ErrorCodePrefixSuffix.XXX_NOT_FOUND, DomainNames.POSE)
        val ROUTINE_NOT_FOUND get() = BusinessLogicException(ErrorCodePrefixSuffix.XXX_NOT_FOUND, DomainNames.ROUTINE)
        val PICKLE_NOT_FOUND get() = BusinessLogicException(ErrorCodePrefixSuffix.XXX_NOT_FOUND, DomainNames.PICKLE)
        val PICKLE_COMMENT_NOT_FOUND
            get() = BusinessLogicException(ErrorCodePrefixSuffix.XXX_NOT_FOUND, DomainNames.PICKLE_COMMENT)
        val PICKLE_REPLY_NOT_FOUND
            get() = BusinessLogicException(ErrorCodePrefixSuffix.XXX_NOT_FOUND, DomainNames.PICKLE_REPLY)
        val PURPOSE_NOT_FOUND
            get() = BusinessLogicException(ErrorCodePrefixSuffix.XXX_NOT_FOUND, DomainNames.PURPOSE)
        val DAILY_NOT_FOUND get() = BusinessLogicException(ErrorCodePrefixSuffix.XXX_NOT_FOUND, DomainNames.PURPOSE)

        // Conflict
        val DUPLICATED_NICKNAME get() = BusinessLogicException(409, "Duplicated Nickname")
        val ALREADY_EXIST_USER
            get() = BusinessLogicException(ErrorCodePrefixSuffix.ALREADY_EXISTS_XXX, DomainNames.USER)
        val ALREADY_EXIST_PICKLE
            get() = BusinessLogicException(ErrorCodePrefixSuffix.ALREADY_EXISTS_XXX, DomainNames.PICKLE)
        val OTHER_ROUTINE_ALREADY_USING_AT_DAY_OF_WEEK
            get() = BusinessLogicException(409, "Other Routine Already Using At Day of week")
    }
}

/**
 * 비즈니스 로직 실행 중에 권한 부족 등으로 인해 발생한 예외
 *
 * @see MaeumGaGymException
 */
class SecurityException(
    status: Int,
    message: String
) : MaeumGaGymException(status, message) {

    constructor(errorCodePrefixSuffix: ErrorCodePrefixSuffix, domainName: DomainNames) :
        this(
            status = errorCodePrefixSuffix.status,
            message = if (errorCodePrefixSuffix.isPrefix) {
                errorCodePrefixSuffix.message + domainName.value
            } else {
                domainName.value + errorCodePrefixSuffix.message
            }
        )

    companion object {

        val INVALID_TOKEN get() = SecurityException(401, "Invalid Token")

        val PERMISSION_DENIED get() = SecurityException(403, "Permission Denied")
    }
}

/**
 * [BusinessLogicException] 발생 시에 [message] 표준화를 위해 사용하는 에러 코드 접두사/접미사
 *
 * Sample: "찾을 수 없는 피클" 예외 발생 상황
 *
 * | [BusinessLogicException]
 * ([ErrorCodePrefixSuffix.NOT_FOUND],
 * [DomainNames.PICKLE])
 *
 * @see BusinessLogicException
 */
enum class ErrorCodePrefixSuffix(
    val status: Int,
    val message: String,
    val isPrefix: Boolean
) {
    XXX_NOT_FOUND(404, " Not Found", false),
    XXX_PERMISSION_DENIED(403, " Permission Denied", false),
    ALREADY_EXISTS_XXX(409, "Already Exists ", true)
}

/**
 * 필터, 정확히는 커스텀 필터 실행 중에 발생한 예외
 *
 * @see MaeumGaGymException
 */
class FilterException(
    status: Int,
    message: String
) : MaeumGaGymException(status, message)

/**
 * 인터셉터, 정확히는 커스텀 인터셉터 실행 중에 발생한 예외
 *
 * @see MaeumGaGymException
 */
class InterceptorException(
    status: Int,
    message: String
) : MaeumGaGymException(status, message)

/**
 * 컨트롤러에서의 유효성 검증 과정 중 발생한 예외
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
class AuthenticationException(
    status: Int,
    message: String
) : MaeumGaGymException(status, message) {

    companion object {

        // UnAuthorized
        val INVALID_TOKEN get() = AuthenticationException(401, "Invalid Token")
        val EXPIRED_TOKEN get() = AuthenticationException(401, "Expired Token")
        val UNAUTHORIZED get() = AuthenticationException(401, "Unauthorized")
        val ROLE_REQUIRED get() = AuthenticationException(403, "Role Required")
    }
}

class FeignException(
    status: Int,
    message: String
) : MaeumGaGymException(status, message) {

    companion object {

        val FEIGN_BAD_REQUEST get() = FeignException(400, "Feign Bad Request")
        val FEIGN_UNAUTHORIZED get() = FeignException(401, "Feign UnAuthorized")
        val FEIGN_FORBIDDEN get() = FeignException(403, "Feign Forbidden")
        val FEIGN_SERVER_ERROR get() = FeignException(500, "Feign Server Error")
        val FEIGN_UNKNOWN_CLIENT_ERROR get() = FeignException(500, "Feign Unknown Error")
    }
}

/**
 * 서버에 타격을 줄 수 있는 예외
 *
 * @see MaeumGaGymException
 */
class CriticalException(
    status: Int,
    message: String
) : MaeumGaGymException(status, message)
