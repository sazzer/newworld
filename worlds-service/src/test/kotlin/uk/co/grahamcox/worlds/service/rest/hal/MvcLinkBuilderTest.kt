package uk.co.grahamcox.worlds.service.rest.hal

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import uk.co.grahamcox.worlds.service.CurrentRequest
import uk.co.grahamcox.worlds.service.users.rest.UserController
import java.net.URI

/**
 * Unit tests for the MVC Link Builder
 */
@CurrentRequest("http://localhost")
internal class MvcLinkBuilderTest {
    /**
     * Build a link to a user record
     */
    @Test
    fun buildUserLink() {
        val testSubject: LinkBuilder<String> = MvcLinkBuilder(UserController::getUser)

        val link = testSubject.buildLink("123")

        Assertions.assertAll(
                Executable { Assertions.assertEquals("http://localhost/api/users/123", link.href) },
                Executable { Assertions.assertFalse(link.templated) },
                Executable { Assertions.assertEquals("application/hal+json", link.type) }
        )
    }

    /**
     * Build a link to change a users password from the ID
     */
    @Test
    fun buildChangePasswordLinkId() {
        val testSubject: LinkBuilder<String> = MvcLinkBuilder(UserController::changePassword, null)

        val link = testSubject.buildLink("123")

        Assertions.assertAll(
                Executable { Assertions.assertEquals("http://localhost/api/users/123/password", link.href) },
                Executable { Assertions.assertFalse(link.templated) },
                Executable { Assertions.assertNull(link.type) }
        )
    }


    /**
     * Build a link to change a users password from the Interface
     */
    @Test
    fun buildChangePasswordLinkInterface() {
        val testSubject: LinkBuilder<MvcLinkData> = MvcLinkBuilder(UserController::changePassword, null)

        val link = testSubject.buildLink(object : MvcLinkData {
            /**
             * The parameters to the MVC method
             */
            override val params: Array<Any?> = arrayOf("123", null, null)
        })

        Assertions.assertAll(
                Executable { Assertions.assertEquals("http://localhost/api/users/123/password", link.href) },
                Executable { Assertions.assertFalse(link.templated) },
                Executable { Assertions.assertNull(link.type) }
        )
    }
}
