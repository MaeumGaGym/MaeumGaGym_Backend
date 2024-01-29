package com.info.maeumgagym.controller.public

import com.info.common.WebAdapter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Tag(name = "Public API")
@Validated
@WebAdapter
@RequestMapping("/public")
class PublicController {

    @GetMapping("/csrf")
    @ResponseStatus(HttpStatus.OK)
    fun getCSRFToken() {
    }
}
