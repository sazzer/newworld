package uk.co.grahamcox.worlds.service.rest

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Controller Advice for various validation cases
 */
@RestControllerAdvice
class ValidationControllerAdvice {

    /**
     * Handle an error reading an input JSON with a missing field
     */
    @ExceptionHandler(MissingKotlinParameterException::class)
    fun handleMissingParameter(e: MissingKotlinParameterException): ResponseEntity<MissingParameterProblem> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.parseMediaType("application/problem+json"))
                .body(MissingParameterProblem(
                        e.path.map { it.fieldName }.joinToString("/")
                ))
    }
}
