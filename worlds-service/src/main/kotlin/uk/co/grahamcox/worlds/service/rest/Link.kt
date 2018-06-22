package uk.co.grahamcox.worlds.service.rest

import org.springframework.http.ResponseEntity

/**
 * Add a link to the schema for the response
 * @param link The link to the schema
 * @return this, for chaining
 */
fun ResponseEntity.BodyBuilder.schemaLink(link: String): ResponseEntity.BodyBuilder {
    this.header("Link", """<$link>;rel="describedby";type="application/schema-instance+json"""")
    return this
}
