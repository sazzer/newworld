package uk.co.grahamcox.worlds.service.openid.webapp

/**
 * Exception to indicate that some mandatory parameters were missing from the Authorize request
 * @property missingParameters The parameters that were missing
 */
class MissingParametersException(val missingParameters: List<String>) : RuntimeException()
