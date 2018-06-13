// @flow

import React from 'react';
import {connectStore} from "redux-box";
import type {UserModule} from '../../users';
import {module as usersModule} from '../../users';
import LoginMenu from './LoginMenu';
import LoggedInMenu from './LoggedInMenu';

/** The flow type representing the props for the User Menu */
type UserMenuProps = {
    hasCurrentUser: boolean
};

/**
 * React component to represent the area of the header bar that is clicked to log in
 */
export function UserMenu(props: UserMenuProps) {
    if (props.hasCurrentUser) {
        return <LoggedInMenu />;
    } else {
        return <LoginMenu />;
    }
}

/**
 * Wrapper around the User Menu that connects it to Redux
 */
export function ConnectedUserMenu({users}: {users: UserModule}) {
    return (
        <UserMenu hasCurrentUser={users.selectHasCurrentUser()} />
    )
}

export default connectStore({users: usersModule})(ConnectedUserMenu);
