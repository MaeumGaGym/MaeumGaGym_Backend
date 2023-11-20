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

    // Internal Server Error
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    // UnAuthorization
    INVALID_TOKEN(401, "Invalid Token"),
    EXPIRED_TOKEN(401, "Expired Token"),

    // Forbidden
    ACCESS_DENIED_QUESTION(403, "No Permission To Access Question"),
    ACCESS_DENIED_REPLY(403, "No Permission To Comment Question"),
    FEED_WRITER_MISMATCH(403, "Feed Writer Mismatch"),

    // Not Found
    QUESTION_NOT_FOUND(404, "Question Not Found"),
    REPLY_NOT_FOUND(404, "Reply Not Found"),
    FAQ_NOT_FOUND(404, "Faq Not Found"),

    // Conflict
    REPLY_EXISTS(409, "Reply Already Exists")
}
