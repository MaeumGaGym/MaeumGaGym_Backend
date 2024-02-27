package com.info.maeumgagym.controller.statistics

import com.info.common.WebAdapter
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.headers.Header
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
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

    @Operation(summary = "CSRF Token 발급 받기")
    @ApiResponse(
        responseCode = "200",
        headers = [
            Header(
                name = "Set-Cookie",
                description = "CSRF Token",
                schema = Schema(type = "string", example = "XSRF-TOKEN=...; Secure; HttpOnly; SameSite=Strict")
            )
        ]
    )
    @GetMapping("/csrf")
    @ResponseStatus(HttpStatus.OK)
    fun getCSRFToken() {
    }
}
