package uk.co.grahamcox.worlds.service.home

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import uk.co.grahamcox.worlds.service.rest.hal.Link
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaMethod

/**
 * Representation of the link details for the home document
 */
class LinkParams(
        val name: String,
        val handler: KFunction<*>,
        params: Map<String, Any?>,
        val templated: Boolean = true,
        val type: String? = null
) {
    /** The Java Method of the handler */
    private val javaMethod = handler.javaMethod!!

    /** The Java Class of the handler */
    private val javaClass = javaMethod.declaringClass

    /** The determined parameters for the handler */
    private val linkParams = javaMethod.parameters.map { params[it.name] }.toTypedArray()

    /** The URI to the handler */
    val uri: String
        get() = MvcUriComponentsBuilder.fromMethod(javaClass, javaMethod, *linkParams)
                .build()
                .toUriString()
}

/**
 * Controller to get the home document for the API
 */
@RestController
@RequestMapping("/api")
class HomeController(
        private val links: List<LinkParams>
) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(HomeController::class.java)
    }

    /**
     * Get the home controller for the API
     */
    @RequestMapping(method = [RequestMethod.GET])
    fun getHome(): Map<String, Map<String, Link>> {
        val builtLinks = links.map {
            val key = it.name
            val uri = it.uri

            LOG.debug("Using URI {} for name {}", uri, key)

            val link = Link(
                    href = uri,
                    templated = it.templated,
                    type = it.type
            )

            LOG.debug("Built link: {}", link)

            key to link
        }.toMap()

        return mapOf(
                "_links" to builtLinks
        )
    }
}
