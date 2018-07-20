package uk.co.grahamcox.worlds.service.acceptance.users

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.http.HttpMethod
import uk.co.grahamcox.worlds.service.acceptance.database.DatabaseExistsVerifier
import uk.co.grahamcox.worlds.service.acceptance.requester.RequestFieldConfig
import uk.co.grahamcox.worlds.service.acceptance.requester.RequestSubmitter
import uk.co.grahamcox.worlds.service.acceptance.requester.ResponseFieldConfig
import uk.co.grahamcox.worlds.service.acceptance.requester.ResponseMatcher
import uk.co.grahamcox.worlds.service.acceptance.stripUri

/**
 * Spring Configuration for testing of users
 */
@Configuration
class UsersConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean("userResponseMatcher") {
                ResponseMatcher(
                        ref(),
                        mapOf(
                                "ID" to ResponseFieldConfig(
                                        fieldPath = "body/id"
                                ),
                                "ETag" to ResponseFieldConfig(
                                        fieldPath = "headers/etag",
                                        expectedConversion = { listOf(it) }
                                ),
                                "Last Modified" to ResponseFieldConfig(
                                        fieldPath = "headers/last-modified",
                                        expectedConversion = { listOf(it) }
                                ),
                                "Email" to ResponseFieldConfig(
                                        fieldPath = "body/email",
                                        expectedConversion = {
                                            when (it) {
                                                "[null]" -> null
                                                else -> it
                                            }
                                        }
                                ),
                                "Username" to ResponseFieldConfig(
                                        fieldPath = "body/username"
                                ),
                                "Display Name" to ResponseFieldConfig(
                                        fieldPath = "body/display_name"
                                ),
                                "Self Link" to ResponseFieldConfig(
                                        fieldPath = "body/_links/self/href",
                                        actualConversion = ::stripUri
                                ),
                                "Change Password Link" to ResponseFieldConfig(
                                        fieldPath = "body/_links[@name='tag:grahamcox.co.uk,2018,links/user/password']/href",
                                        expectedConversion = {
                                            when (it) {
                                                "[null]" -> null
                                                else -> it
                                            }
                                        },
                                        actualConversion = ::stripUri
                                )
                        ))
            }

            bean("userEmailExistsVerifier") {
                DatabaseExistsVerifier(
                        ref(),
                        "SELECT COUNT(*) FROM users WHERE email = :id"
                )
            }

            bean("userRetrievalRequestSubmitter") {
                RequestSubmitter(
                        ref(),
                        "/api/users/{id}",
                        HttpMethod.GET,
                        mapOf(
                                "ID" to RequestFieldConfig(
                                        fieldName = "id"
                                )
                        ),
                        mapOf()
                )
            }

            bean("userUpdateRequestSubmitter") {
                RequestSubmitter(
                        ref(),
                        "/api/users/{id}",
                        HttpMethod.PUT,
                        mapOf(
                                "ID" to RequestFieldConfig("id")
                        ),
                        mapOf(
                                "Email" to RequestFieldConfig("email"),
                                "Username" to RequestFieldConfig("username"),
                                "Display Name" to RequestFieldConfig("display_name")
                        )
                )
            }

            bean("changePasswordRequestSubmitter") {
                RequestSubmitter(
                        ref(),
                        "/api/users/{id}/password",
                        HttpMethod.PUT,
                        mapOf(
                                "ID" to RequestFieldConfig("id")
                        ),
                        mapOf(
                                "Old" to RequestFieldConfig("old_password"),
                                "New" to RequestFieldConfig("new_password")
                        )
                )
            }
        }.initialize(context)
    }
}
