package uk.co.grahamcox.worlds.service.rest

import uk.co.grahamcox.worlds.service.model.Id
import uk.co.grahamcox.worlds.service.model.Resource

/**
 * Interface describing how to build an API model from an internal resource
 */
interface ModelBuilder<in ID : Id, in DATA, out MODEL> {
    /**
     * Build the API Model from the internal resource
     * @param input The input to build the model from
     * @return the model
     */
    fun build(input: Resource<ID, DATA>): MODEL
}
