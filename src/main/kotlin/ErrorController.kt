package com.github.eduhoribe

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.web.ErrorProperties
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import java.util.concurrent.atomic.AtomicInteger
import javax.servlet.http.HttpServletRequest

@Controller
class ErrorController : BasicErrorController(
    DefaultErrorAttributes(),
    ErrorProperties(),
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    val count = AtomicInteger()

    override fun error(request: HttpServletRequest?): ResponseEntity<MutableMap<String, Any>> {
        count.incrementAndGet()
        logger.error(
            """
            
            @@@@@@@@@@@@@@@@@@@@@@@@@
            @ Default error handler @
            @@@@@@@@@@@@@@@@@@@@@@@@@
            """.trimIndent()
        )
        return super.error(request)
    }
}