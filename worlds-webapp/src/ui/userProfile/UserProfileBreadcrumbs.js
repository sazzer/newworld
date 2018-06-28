// @flow

import React from 'react';
import {Breadcrumb} from 'semantic-ui-react'
import {Link} from 'react-router-dom';
import {Interpolate} from 'react-i18next';
import {connectStore} from "redux-box";
import type {UserModule} from "../../users";
import {module as usersModule} from "../../users";
import type {User} from "../../users/users";

/** The flow type representing the props for the User Breadcrumbs */
type UserProfileBreadcrumbsProps = {
    user: ?User
};

/**
 * The breadcrumbs to display for the user profile
 */
export function UserProfileBreadcrumbs(props: UserProfileBreadcrumbsProps) {
    let displayName;
    if (props.user) {
        displayName = props.user.displayName
    } else {
        displayName = '';
    }

    return (
        <Breadcrumb>
            <Breadcrumb.Section>
                <Link to="/">
                    <Interpolate i18nKey="userProfile.breadcrumbs.home" />
                </Link>
            </Breadcrumb.Section>
            <Breadcrumb.Divider />
            <Breadcrumb.Section active>
                <Interpolate i18nKey="userProfile.breadcrumbs.current" />
                <Link to="/profile">
                    { displayName }
                </Link>
            </Breadcrumb.Section>
        </Breadcrumb>
    );
}

/**
 * Wrapper around the User Breadcrumbs that connects it to Redux
 */
export function ConnectedUserProfileBreadcrumbs({users}: {users: UserModule}) {
    return (
        <UserProfileBreadcrumbs user={users.selectCurrentUser()} />
    )
}

export default connectStore({users: usersModule})(ConnectedUserProfileBreadcrumbs);
