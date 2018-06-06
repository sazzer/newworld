// @flow

import React from 'react';
import { Interpolate } from 'react-i18next';
import { Menu } from 'semantic-ui-react'

export default function HeaderBar() {
    return (
        <Menu fixed="top" inverted borderless>
            <Menu.Item header>
                <Interpolate i18nKey="page.title" />
            </Menu.Item>

            <Menu.Menu position="right">
                <Menu.Item>
                    <Interpolate i18nKey="page.login" />
                </Menu.Item>
            </Menu.Menu>
        </Menu>
    );
}
