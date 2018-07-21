package uk.co.grahamcox.worlds.service.rest.hal

import org.slf4j.LoggerFactory
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.lang.reflect.Method
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaMethod

/**
 * Standard implementation of the Link Builder to build a link to an MVC Controller method
 */
class MvcLinkBuilder<in DATA>(
        method: KFunction<*>,
        private val mediaType: String? = "application/hal+json"
) : LinkBuilder<DATA> {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(MvcLinkBuilder::class.java)
    }

    /** The Java Method that is to be used */
    private val javaMethod: Method = method.javaMethod!!

    /** The Java Class that the method is defined on */
    private val javaClass: Class<*> = javaMethod.declaringClass

    /**
     * Actually build the link to the required resource
     * @param data The data to use to build the link
     */
    override fun buildLink(data: DATA): Link {
        LOG.debug("Building URI for target method {} and data {}", javaMethod, data)

        val params: Array<Any?> = when (data) {
            is MvcLinkData -> data.params
            else -> {
                val actual: List<Any?> = listOf(data)
                val remainder = if (javaMethod.parameterCount == 1) {
                    emptyArray()
                } else {
                    Array<Any?>(javaMethod.parameterCount - 1) { null }
                }.toList()

                (actual + remainder).toTypedArray()
            }
        }

        val uri = MvcUriComponentsBuilder.fromMethod(javaClass, javaMethod, *params)
                .build()
                .toUriString()
        LOG.debug("Built URI {} for target method {} and params {}", uri, javaMethod, params)

        return Link(
                href = uri,
                type = mediaType
        )
    }
}
