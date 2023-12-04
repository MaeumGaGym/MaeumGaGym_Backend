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
    ROLE_CONVERTER_ERROR(500, "Role Converter Error"),

    // UnAuthorized
    INVALID_TOKEN(401, "Invalid Token"),
    EXPIRED_TOKEN(401, "Expired Token"),
    UNAUTHORIZED(401, "Un Authorized"),

    // Not Found
    USER_NOT_FOUND(404, "User Not Found"),

    // Bad Request
    ALREADY_WITHDRAWAL_USER(400, "Already Withdrawal User"),

    // Conflict
    DUPLICATED_NICKNAME(409, "Duplicated Nickname"),
    ALREADY_EXIST_USER(409, "Already Exists User")
}
