package uk.co.grahamcox.worlds.service

import org.slf4j.LoggerFactory
import org.springframework.core.io.ResourceLoader
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.ModelAndView
import java.io.FileNotFoundException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Controller for getting Schema definitions
 */
@Controller
@RequestMapping("/schema/**")
class SchemaController(
        private val resourceLoader: ResourceLoader
) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(SchemaController::class.java)
    }

    /**
     * Handle when the requested schema is not found
     */
    @ExceptionHandler(FileNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUnknownSchema() {}

    /**
     * Get the Schema that this request refers to
     */
    @RequestMapping(method = [RequestMethod.GET], produces = ["application/schema-instance+json"])
    @ResponseStatus
    fun getSchema(request: HttpServletRequest, response: HttpServletResponse): Any {
        val schema = request.servletPath.removePrefix("/schema/")
        val template = "/schemas/$schema"

        if (!resourceLoader.getResource("classpath:/templates/$template.ftl").exists()) {
            LOG.warn("Request for unknown schema: {}", schema)
            throw FileNotFoundException("Schema not found: $schema")
        }

        response.contentType = "application/schema-instance+json"
        return ModelAndView(template)
    }
}
