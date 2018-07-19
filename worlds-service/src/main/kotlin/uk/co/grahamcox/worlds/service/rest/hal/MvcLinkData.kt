package uk.co.grahamcox.worlds.service.rest.hal

/**
 * Interface describing the data needed for an MVC Link
 */
interface MvcLinkData {
    /**
     * The parameters to the MVC method
     */
    val params: Array<Any?>
}
