package uk.co.grahamcox.worlds.service.openid.scopes

/**
 * Registry of scopes that are supported, allowing us to process them
 */
class ScopeRegistry(
        scopes: Collection<Scope>
) {
    /**
     * Map of all the scopes by their ID
     */
    val scopesMap = scopes.groupBy { it.id }
            .filter { it.value.isNotEmpty() }
            .onEach {
                if (it.value.size > 1) {
                    throw IllegalArgumentException("Multiple scopes registered with the same ID: ${it.key}")
                }
            }
            .mapValues { it.value.first() }
    /**
     * Parse the given string to build a set of scopes
     */
    fun parseScopeString(input: String): Set<Scope> {
        val scopesStrings = input.split("\\s".toRegex())
                .map(String::trim)
                .filterNot(String::isNullOrBlank)

        val unknown = scopesStrings.filterNot { scopesMap.containsKey(it) }
        if (unknown.isNotEmpty()) {
            throw UnknownScopesException(unknown)
        }

        return scopesStrings.mapNotNull { scopesMap[it] }
                .toSet()
    }
}
