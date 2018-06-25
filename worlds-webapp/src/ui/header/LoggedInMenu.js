// @flow

import React from 'react';
import {Interpolate} from 'react-i18next';
import {Dropdown} from 'semantic-ui-react'
import {connectStore} from "redux-box";
import type {UserModule} from '../../users';
import {module as usersModule} from '../../users';
import type {User} from "../../users/users";

/** The flow type representing the props for the LoggedIn Menu */
type LoggedInMenuProps = {
    user: ?User
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
        <Dropdown item text={username}>
            <Dropdown.Menu>
                <Dropdown.Item>
                    <Interpolate i18nKey="page.userMenu.logout" />
                </Dropdown.Item>
            </Dropdown.Menu>
        </Dropdown>
    );
}

/**
 * Wrapper around the LoggedIn Menu that connects it to Redux
 */
export function ConnectedLoggedInMenu({users}: {users: UserModule}) {
    return (
        <LoggedInMenu user={users.selectCurrentUser()} />
    )
}

export default connectStore({users: usersModule})(ConnectedLoggedInMenu);
