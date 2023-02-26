package gr.alx.common.application.error

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


private val log = KotlinLogging.logger {}

/**
 * Handles many spring exceptions and also should handle custom exceptions.
 */
@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    /**
     * Exception thrown from Hibernate entity validations.
     */
    @ExceptionHandler(ConstraintViolationException::class)
    protected fun handleConstraintViolationException(
        ex: ConstraintViolationException,
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        return try {
            val messages: List<String> = ex.constraintViolations.map { "${it.propertyPath} ${it.message}" }
            ResponseEntity(messages, HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    /**
     * Exception thrown from Spring DTO validations.
     */
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val messages: List<String> = ex.bindingResult.fieldErrors.map { "${it.field} ${it.defaultMessage}" }
        return ResponseEntity(messages, HttpStatus.BAD_REQUEST)

    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        log.error { ex.stackTraceToString() }
        return super.handleHttpMessageNotReadable(ex, headers, status, request)
    }
}
