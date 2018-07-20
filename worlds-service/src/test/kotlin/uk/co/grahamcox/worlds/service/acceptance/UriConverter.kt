package uk.co.grahamcox.worlds.service.acceptance

import org.springframework.web.util.UriComponentsBuilder

/**
 * Strip bits from the URI to only leave the path, query and fragment components
 */
fun stripUri(uri: Any?) =
        uri?.let {
            UriComponentsBuilder.fromUriString(uri as String)
                    .scheme(null)
                    .host(null)
                    .port(null)
                    .build()
                    .toUriString()
        }
