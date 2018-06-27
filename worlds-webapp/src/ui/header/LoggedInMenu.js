// @flow

import React from 'react';
import {Interpolate} from 'react-i18next';
import {Dropdown} from 'semantic-ui-react'
import {connectStore} from "redux-box";
import {Link} from 'react-router-dom';
import type {UserModule} from '../../users';
import type {AuthModule} from '../../auth';
import {module as usersModule} from '../../users';
import {module as authModule} from '../../auth';
import type {User} from "../../users/users";
import './LoggedInMenu.css';

/** The flow type representing the props for the LoggedIn Menu */
type LoggedInMenuProps = {
    user: ?User,
    onLogout: () => void
};

/**
 * React component to represent the area of the header bar that shows the menu when a user is logged in
 */
export function LoggedInMenu(props: LoggedInMenuProps) {
    let username;
    if (props.user) {
        username = props.user.displayName;
    }

    return (
        <Dropdown item text={username} className="worlds-loggedInMenu">
            <Dropdown.Menu>
                <Dropdown.Item>
                    <Link to="/profile">
                        <Interpolate i18nKey="page.userMenu.profile" />
                    </Link>
                </Dropdown.Item>
                <Dropdown.Item onClick={props.onLogout} >
                    <Interpolate i18nKey="page.userMenu.logout" />
                </Dropdown.Item>
            </Dropdown.Menu>
        </Dropdown>
    );
}

/**
 * Wrapper around the LoggedIn Menu that connects it to Redux
 */
export function ConnectedLoggedInMenu({users, auth}: {users: UserModule, auth: AuthModule}) {
    return (
        <LoggedInMenu user={users.selectCurrentUser()} onLogout={auth.logout} />
    )
}

export default connectStore({users: usersModule, auth: authModule})(ConnectedLoggedInMenu);
