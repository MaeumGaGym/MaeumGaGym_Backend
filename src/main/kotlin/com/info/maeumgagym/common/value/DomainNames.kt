package com.info.maeumgagym.common.value

enum class DomainNames(
    val value: String,
    val lowCaseValue: String
) {

    USER("User", "user"),
    PICKLE("Pickle", "pickle"),
    PICKLE_LIKE("Pickle", "pickle"),
    PICKLE_COMMENT("Pickle Comment", "pickle comment"),
    PICKLE_REPLY("Pickle Reply", "pickle reply"),
    POSE("Pose", "pose"),
    ROUTINE("Routine", "routine"),
    ROUTINE_HISTORY("Routine History", "routine history"),
    WAKA_TIME("Waka Time", "waka time"),
    REPORT("Report", "report"),
    PURPOSE("Purpose", "purpose"),
    STEP("Step", "step"),
    DAILY("Daily", "daily")
}
