package com.info.maeumgagym.infrastructure.threadsafe

/**
 * [ThreadLocal]을 Immutable 상태로 만들기 위한 Wrapper
 *
 * 어디서나 접근 가능했던 재설정 메소드([ThreadLocal.set])를 생성 시에만 호출하도록 [createWith] 메소드로 감쌈
 *
 * @see ThreadLocal
 *
 * @since 27-05-2024
 * @author Daybreak312
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
