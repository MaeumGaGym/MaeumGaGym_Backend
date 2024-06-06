package com.info.maeumgagym.application

object TableNames {

    private const val TABLE_PREFIX = "tbl_"
    private const val INDEX_PREFIX = "idx_"
    private const val UNIQUE_PREFIX = "unique_idx_"

    const val USER_TABLE = "${TABLE_PREFIX}user"

    const val POSE_TABLE = "${TABLE_PREFIX}pose"

    const val ROUTINE_TABLE = "${TABLE_PREFIX}routine"
    const val ROUTINE_EXERCISE_TABLE = "${TABLE_PREFIX}routine_exercise"

    const val ROUTINE_HISTORY_TABLE = "${TABLE_PREFIX}routine_history"
    const val ROUTINE_HISTORY_DATE_USER = INDEX_PREFIX + "date_user"
    const val ROUTINE_EXERCISE_HISTORY_TABLE = "${TABLE_PREFIX}routine_exercise_history"

    const val PICKLE_TABLE = "${TABLE_PREFIX}pickle"
    const val PICKLE_MAP_TABLE = "${TABLE_PREFIX}pickle_comments"
    const val PICKLE_COMMENT_TABLE = "${TABLE_PREFIX}pickle_comment"
    const val PICKLE_REPLY_TABLE = "${TABLE_PREFIX}pickle_reply"

    const val PICKLE_TAG_INDEX = "${INDEX_PREFIX}tag"

    const val PICKLE_LIKE_TABLE = "${TABLE_PREFIX}pickle_like"
    const val PICKLE_LIKE_INDEX = "${INDEX_PREFIX}like"

    const val REPORT_TABLE = "${TABLE_PREFIX}report"

    const val WAKA_TIME_TABLE = "${TABLE_PREFIX}waka_time"

    const val ACCESS_TOKEN_TABLE = "${TABLE_PREFIX}access"
    const val REFRESH_TOKEN_TABLE = "${TABLE_PREFIX}refresh"
    const val REVOKED_MAEUMGAGYM_TOKEN_TABLE = "${TABLE_PREFIX}revoked_mgt"

    const val REDIS_STEP_TABLE = "${TABLE_PREFIX}step"

    const val PURPOSE_TABLE = "${TABLE_PREFIX}purpose"
    const val PURPOSE_START_DATE_INDEX = "${INDEX_PREFIX}start"
    const val PURPOSE_END_DATE_INDEX = "${INDEX_PREFIX}end"

    const val DAILY = TABLE_PREFIX + "daily"
    const val DAILY_DATE_INDEX = INDEX_PREFIX + "date"
    const val DAILY_DATE_UPLOADER_UNIQUE = UNIQUE_PREFIX + "date_uploader"
}
