import React from 'react';
import {storiesOf} from '@storybook/react';
import { withKnobs, text, boolean, number } from '@storybook/addon-knobs/react';
import UserProfileBreadcrumbs from '../UserProfileBreadcrumbs';

storiesOf('UserProfileBreadcrumbs', module)
    .addDecorator(withKnobs)
    .addWithJSX('UserProfileBreadcrumbs', () => {
        const displayName = text('Display Name', 'Test User');

        return (
            <UserProfileBreadcrumbs user={{
                id: 'id',
                username: 'username',
                displayName: displayName
            }} />
        );
    }, {
        showFunctions: false
    })
;
