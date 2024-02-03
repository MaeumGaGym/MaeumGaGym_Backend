package com.info.maeumgagym.common.exception

enum class ErrorCode(
    val status: Int,
    val message: String
) {
    // Feign
    FEIGN_BAD_REQUEST(400, "Feign Bad Request"),
    FEIGN_UNAUTHORIZED(401, "Feign UnAuthorized"),
    FEIGN_FORBIDDEN(403, "Feign Forbidden"),
    FEIGN_SERVER_ERROR(500, "Feign Server Error"),

    // No Content
    THERE_NO_PICKLES(204, "업로드된 피클이 존재하지 않습니다."),

    // Internal Server Error
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    ROLE_CONVERTER_ERROR(500, "Role Converter Error"),

    // UnAuthorized
    INVALID_TOKEN(401, "Invalid Token"),
    EXPIRED_TOKEN(401, "Expired Token"),
    UNAUTHORIZED(401, "Un Authorized"),

    // Forbidden
    PERMISSION_DENIED(403, "Permission Denied"),
    VIDEO_AND_UPLOADER_MISMATCHED(403, "Video And Uploader Mismatched"),

    // Not Found
    USER_NOT_FOUND(404, "User Not Found"),
    POSE_NOT_FOUND(404, "Pose Not Found"),
    ROUTINE_NOT_FOUND(404, "Routine Not Found"),
    PICKLE_NOT_FOUND(404, "Pickle Not Found"),
    PICKLE_COMMENT_NOT_FOUND(404, "Pickle Comment Not Found"),
    PICKLE_REPLY_NOT_FOUND(404, "Pickle Reply Not Found"),
    STEP_NOT_FOUND(404, "Step Not Found"),

    // Bad Request
    FILE_TYPE_MISMATCHED(400, "File Type Mismatched"),
    EXERCISE_LIST_CANNOT_EMPTY(400, "Exercise list cannot empty"),
    PICKLE_MISMATCHED(400, "Pickle Mismatched"),
    WAKA_STARTED_NOT_YET(400, "Wakatime Started Not Yet"),
    CANNNOT_REPORT_ONESELF(400, "Cannot Report Oneself"),
    NOT_UPLOADED_TO_VIDEO_SERVER(404, "Does Not Uploaded In Video Server"),
    TAG_TOO_LONG(404, "Tag Too Long, Tag Cannot Longer than 10"),

    // Conflict
    DUPLICATED_NICKNAME(409, "Duplicated Nickname"),
    ALREADY_EXIST_USER(409, "Already Exists User"),
    ALREADY_EXIST_PICKLE(409, "Already Exists Pickle"),
    ALREADY_EXIST_STEP(409, "Alreay Exists Step"),
    ALREADY_STARTED_WAKA(409, "Already Started Wakatime"),
    OTHER_ROUTINE_ALREADY_USING_AT_DAY_OF_WEEK(409, "Other Routine Already Using At Day of week")
}
