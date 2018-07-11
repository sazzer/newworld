import React from 'react';
import {storiesOf} from '@storybook/react';
import {text, withKnobs} from '@storybook/addon-knobs/react';
import UserProfileBreadcrumbs from '../UserProfileBreadcrumbs';

storiesOf('UserProfile/Breadcrumbs', module)
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
