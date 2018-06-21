// @flow

import React from 'react';
import {Interpolate} from 'react-i18next';
import {Menu} from 'semantic-ui-react'
import {connectStore} from "redux-box";
import type {AuthModule} from '../../auth';
import {module as authModule} from '../../auth';

/** The flow type representing the props for the Login Menu */
type LoginMenuProps = {
    onClickLogin: () => void,
};

/**
 * React component to represent the area of the header bar that is clicked to log in
 */
export function LoginMenu(props: LoginMenuProps) {
    return (
        <Menu.Item onClick={props.onClickLogin}>
            <Interpolate i18nKey="page.login" />
        </Menu.Item>
    );
}

/**
 * Wrapper around the Login Menu that connects it to Redux
 */
export function ConnectedLoginMenu({auth}: {auth: AuthModule}) {
    return (
        <LoginMenu onClickLogin={auth.startAuth} />
    )
}

export default connectStore({auth: authModule})(ConnectedLoginMenu);
