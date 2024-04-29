//package com.info.maeumgagym.infrastructure.config.annotation
//
//import com.info.maeumgagym.common.annotation.security.Permitted
//import com.info.maeumgagym.common.annotation.security.RequireAuthentication
//import org.aspectj.lang.ProceedingJoinPoint
//import org.aspectj.lang.annotation.Around
//import org.aspectj.lang.annotation.Aspect
//import org.aspectj.lang.annotation.Before
//import org.aspectj.lang.annotation.Pointcut
//import org.aspectj.lang.reflect.MethodSignature
//import org.springframework.stereotype.Component
//
//
//@Aspect
//@Component
//class AnnotationAspect {
//
//    @Pointcut("execution(* com.info.maeumgagym.presentation.controller.*.*Controller.*(..))")
//    fun controllerMethods() {}
//
//    @Before("execution(@com.info.maeumgagym.common.annotation.security.RequireAuthentication * *(..)) && @annotation(permitted)")
//    fun checkAccessControl(permitted: Permitted?) {
//        throw Exception("@Permitted cannot be used together with @RequireAuthentication on the same method.1")
//    }
//
//    @Around("controllerMethods() && @annotation(com.info.maeumgagym.common.annotation.security.Permitted)")
//    fun checkPermittedAndParamRequireAuthentication(joinPoint: ProceedingJoinPoint): Any {
//        val checks = (joinPoint.signature as MethodSignature).method.parameterAnnotations.any {
//            it.any { annotation -> annotation.annotationClass == RequireAuthentication::class }
//        }
//
//        if (checks) {
//            throw Exception("@Permitted cannot be used together with @RequireAuthentication on the same method.2")
//        } else return joinPoint.proceed()
//    }
//
//
//    @Around("controllerMethods() && @annotation(com.info.maeumgagym.common.annotation.security.Permitted) && @annotation(com.info.maeumgagym.common.annotation.security.RequireAuthentication)")
//    fun checkPermittedAndRequireAuthentication(joinPoint: ProceedingJoinPoint): Any? {
//        throw Exception("@Permitted cannot be used together with @RequireAuthentication on the same method.")
//    }
//
//    @Around("controllerMethods() && @annotation(com.info.maeumgagym.common.annotation.security.Permitted) && @annotation(com.info.maeumgagym.common.annotation.security.RequireRole)")
//    fun checkPermittedAndRequireRole(joinPoint: ProceedingJoinPoint): Any? {
//        throw Exception("@Permitted cannot be used together with @RequireRole on the same method.")
//    }
//}
//
