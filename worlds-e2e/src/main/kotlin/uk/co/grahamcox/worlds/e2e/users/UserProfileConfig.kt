package uk.co.grahamcox.worlds.e2e.users

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.e2e.matcher.FormField
import uk.co.grahamcox.worlds.e2e.matcher.FormMatcher

/**
 * Spring configuration for working with User Profiles
 */
@Configuration
class UserProfileConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean {
                FormMatcher(
                        mapOf(
                                "Email Address" to FormField(
                                        getter = UserProfileFormPageModel::email
                                ),
                                "Username" to FormField(
                                        getter = UserProfileFormPageModel::username
                                ),
                                "Display Name" to FormField(
                                        getter = UserProfileFormPageModel::displayName
                                )
                        )
                )

            }
        }.initialize(context)
    }
}
