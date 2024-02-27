package com.info.maeumgagym.error

import com.info.maeumgagym.common.exception.MaeumGaGymException
import org.junit.jupiter.api.function.Executable
import org.opentest4j.AssertionFailedError

internal class TestException(
    val status: Int,
    message: String
) : RuntimeException(message) {

    companion object {
        val WAKATIME_DOES_NOT_SAVED = TestException(500, "와카타임이 저장되지 않았습니다.")
        val ERROR_TOO_BIG = TestException(500, "발생한 오차가 허용된 오차보다 큽니다.")
        val MISMATCH_QUOTE_AND_QUOTER = TestException(500, "명언과 화자가 설정된 것과 일치하지 않습니다.")

        fun assertThrowsMaeumGaGymExceptionInstance(e: MaeumGaGymException, executable: Executable) {
            var thrown = false

            try {
                executable.execute()
            } catch (caughtException: MaeumGaGymException) {
                if (caughtException != e &&
                    caughtException.message != e.message
                ) {
                    throw AssertionFailedError("")
                }
                thrown = true
            }

            if (!thrown) throw AssertionFailedError("")
        }
    }
}
