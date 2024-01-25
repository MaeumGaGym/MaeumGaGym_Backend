package com.info.maeumgagym

object TableNames {

    private const val TABLE_PREFIX = "tbl_"

    const val USER_TABLE = "${TABLE_PREFIX}user"

    const val DELETED_AT_TABLE = "${TABLE_PREFIX}deleted_at"

    const val POSE_TABLE = "${TABLE_PREFIX}pose"

    const val ROUTINE_TABLE = "${TABLE_PREFIX}routine"

    const val PICKLE_TABLE = "${TABLE_PREFIX}pickle"

    const val PICKLE_LIKE_TABLE = "${TABLE_PREFIX}pickle_like"

    const val PICKLE_MAP_TABLE = "${TABLE_PREFIX}pickle_comments"

    const val PICKLE_COMMENT_TABLE = "${TABLE_PREFIX}pickle_comment"

    const val PICKLE_REPLY_TABLE = "${TABLE_PREFIX}pickle_reply"

    const val ACCESS_TOKEN_TABLE = "${TABLE_PREFIX}access"

    const val REFRESH_TOKEN_TABLE = "${TABLE_PREFIX}refresh"

    const val WAKA_TIME_TABLE = "${TABLE_PREFIX}waka_time"

    const val REPORT_TABLE = "${TABLE_PREFIX}report"
}
