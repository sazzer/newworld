// @flow

import React from 'react';
import {Interpolate} from 'react-i18next';
import {Menu} from 'semantic-ui-react'
import UserMenu from './UserMenu';

/**
 * React Component representing the header bar of the application
 */
export default function HeaderBar() {
    return (
        <Menu fixed="top" inverted borderless>
            <Menu.Item header>
                <Interpolate i18nKey="page.title" />
            </Menu.Item>

            <Menu.Menu position="right">
                <UserMenu />
            </Menu.Menu>
        </Menu>
    );
}
