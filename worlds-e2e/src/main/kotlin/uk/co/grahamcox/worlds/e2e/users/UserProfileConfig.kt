package uk.co.grahamcox.worlds.e2e.users

import org.springframework.context.annotation.Configuration
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import uk.co.grahamcox.worlds.e2e.form.FormWrapper
import uk.co.grahamcox.worlds.e2e.form.FormField

/**
 * Spring configuration for working with User Profiles
 */
@Configuration
class UserProfileConfig(context: GenericApplicationContext) {
    init {
        beans {
            bean("userProfileFormWrapper") {
                FormWrapper(
                        mapOf(
                                "Email Address" to FormField(
                                        property = UserProfileFormPageModel::email
                                ),
                                "Username" to FormField(
                                        property = UserProfileFormPageModel::username
                                ),
                                "Display Name" to FormField(
                                        property = UserProfileFormPageModel::displayName
                                )
                        )
                )
            }
            bean("changePasswordFormWrapper") {
                FormWrapper(
                        mapOf(
                                "Old Password" to FormField(
                                        property = ChangePasswordFormPageModel::oldPassword
                                ),
                                "New Password" to FormField(
                                        property = ChangePasswordFormPageModel::newPassword
                                ),
                                "Repeat Password" to FormField(
                                        property = ChangePasswordFormPageModel::repeatPassword
                                )
                        )
                )
            }
        }.initialize(context)
    }
}