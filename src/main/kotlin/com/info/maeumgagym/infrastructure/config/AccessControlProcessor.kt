//package com.info.maeumgagym.infrastructure.config.annotation
//
//import com.google.auto.service.AutoService
//import com.info.maeumgagym.common.annotation.security.Permitted
//import com.info.maeumgagym.common.annotation.security.RequireAuthentication
//import javax.annotation.processing.*
//import javax.lang.model.SourceVersion
//import javax.lang.model.element.TypeElement
//import javax.tools.Diagnostic
//
//@SupportedAnnotationTypes(
//    "com.info.maeumgagym.common.annotation.security.RequireAuthentication",
//    "com.info.maeumgagym.common.annotation.security.Permitted"
//)
//@SupportedSourceVersion(SourceVersion.RELEASE_17)
//@AutoService(Processor::class)
//class AccessControlProcessor : AbstractProcessor() {
//
//    override fun process(annotations: Set<TypeElement?>?, roundEnv: RoundEnvironment): Boolean {
//        processingEnv.messager.printMessage(
//            Diagnostic.Kind.NOTE,
//            "Access Control Processor enabled"
//        )
//
//        roundEnv.getElementsAnnotatedWith(Permitted::class.java).forEach {
//            if (it.getAnnotation(RequireAuthentication::class.java) != null) {
//                processingEnv.messager.printMessage(
//                    Diagnostic.Kind.ERROR,
//                    "@Permitted cannot be used together with @RequireAuthentication on the same method.",
//                    it
//                )
//            }
//        }
//
//        return true
//    }
//}
