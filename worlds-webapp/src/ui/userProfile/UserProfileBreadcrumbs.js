// @flow

import React from 'react';
import {Breadcrumb} from 'semantic-ui-react'
import {Link} from 'react-router-dom';
import {Interpolate} from 'react-i18next';
import type {User} from "../../users/users";

/** The flow type representing the props for the User Breadcrumbs */
type UserProfileBreadcrumbsProps = {
    user: User
};

/**
 * The breadcrumbs to display for the user profile
 */
export default function UserProfileBreadcrumbs(props: UserProfileBreadcrumbsProps) {
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
                    { props.user.displayName }
                </Link>
            </Breadcrumb.Section>
        </Breadcrumb>
    );
}
