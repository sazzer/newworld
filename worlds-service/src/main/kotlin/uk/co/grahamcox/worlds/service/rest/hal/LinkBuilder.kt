package uk.co.grahamcox.worlds.service.rest.hal

/**
 * Helper interface to build a link to a resource
 */
interface LinkBuilder<in DATA> {
    /**
     * Actually build the link to the required resource
     * @param data The data to use to build the link
     */
    fun buildLink(data: DATA) : Link
}
