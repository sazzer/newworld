package uk.co.grahamcox.worlds.service.openid.scopes

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import uk.co.grahamcox.worlds.service.openid.OpenIdScopes

/**
 * Unit tests for the ScopeRegistry
 */
internal class ScopeRegistryTest {
    /** The test subject */
    private val testSubject = ScopeRegistry(OpenIdScopes.values().toList())

    /**
     * Test creating the registry with diplicate values
     */
    @Test
    fun createDuplicates() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            ScopeRegistry(listOf(
                    OpenIdScopes.OPENID,
                    OpenIdScopes.OPENID
            ))
        }
    }

    /**
     * Test parsing scope strings into the acutal scopes, in cases where it works correctly
     */
    @TestFactory
    fun parseString() =
            mapOf<String, Set<Scope>>(
                    "" to setOf(),
                    " " to setOf(),
                    "openid" to setOf(OpenIdScopes.OPENID),
                    " openid " to setOf(OpenIdScopes.OPENID),
                    "openid openid" to setOf(OpenIdScopes.OPENID),
                    "openid email profile" to setOf(OpenIdScopes.OPENID, OpenIdScopes.EMAIL, OpenIdScopes.PROFILE),
                    "openid    email    profile" to setOf(OpenIdScopes.OPENID, OpenIdScopes.EMAIL, OpenIdScopes.PROFILE),
                    "openid\temail\tprofile" to setOf(OpenIdScopes.OPENID, OpenIdScopes.EMAIL, OpenIdScopes.PROFILE),
                    "openid\nemail\nprofile" to setOf(OpenIdScopes.OPENID, OpenIdScopes.EMAIL, OpenIdScopes.PROFILE)
            ).map { DynamicTest.dynamicTest("Scopes: ${it.key}") {
                val parsed = testSubject.parseScopeString(it.key)
                Assertions.assertEquals(parsed, it.value)
            }
            }

    /**
     * Test parsing scope strings into the acutal scopes, in cases where it fails
     */
    @TestFactory
    fun parseStringUnknownScopes() =
            mapOf(
                    "-" to setOf("-"),
                    "openId" to setOf("openId"),
                    "openid,email" to setOf("openid,email"),
                    "unknown" to setOf("unknown"),
                    "-openid" to setOf("-openid")
            ).map { DynamicTest.dynamicTest("Scopes: ${it.key}") {
                val e = Assertions.assertThrows(UnknownScopesException::class.java) {
                    testSubject.parseScopeString(it.key)
                }

                Assertions.assertEquals(it.value, e.unknownScopes.toSet())
            }
            }
}
