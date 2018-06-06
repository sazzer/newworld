// @flow

import React from 'react';
import { Menu } from 'semantic-ui-react'

export default function HeaderBar() {
    return (
        <Menu fixed="top" inverted borderless>
            <Menu.Item header>Worlds</Menu.Item>

            <Menu.Menu position="right">
                <Menu.Item>Log In / Register</Menu.Item>
            </Menu.Menu>
        </Menu>
    );
}
