package uk.co.grahamcox.worlds.service.rest.hal

/**
 * Representation of a Link to another resource
 */
data class Link(
        val href: String,
        val templated: Boolean = false,
        val type: String? = "application/hal+json",
        val deprecation: String? = null,
        val name: String? = null,
        val profile: String? = null,
        val title: String? = null,
        val hreflang: String? = null
)
