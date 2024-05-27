package com.info.maeumgagym.infrastructure.threadsafe

/**
 * Thread 단위로 값을 사용하기 위한 [ThreadLocal]을 추상화한 클래스
 *
 * 개선점
 * - 어디서나 재설정 가능했던 [ThreadLocal.set]을 생성 시에만 호출하도록 [createWith] 메소드로 감싸 immutable 상태로 수정
 *   더 안정적이며 신뢰성 있는 값을 제공받을 수 있음
 */
final class ThreadValue<T> private constructor(
    private val threadLocal: ThreadLocal<T>
) {

    companion object {
        fun <T> createWith(value: T): ThreadValue<T> =
            ThreadValue<T>(
                ThreadLocal.withInitial { value }
            )
    }

    fun getValue(): T = this.threadLocal.get()

    fun destroy() {
        this.threadLocal.remove()
    }
}
