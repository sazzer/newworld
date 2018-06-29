import React from 'react';
import {storiesOf} from '@storybook/react';
import { withKnobs, text, boolean, number } from '@storybook/addon-knobs/react';
import UserProfileHeader from '../UserProfileHeader';

storiesOf('UserProfileHeader', module)
    .addDecorator(withKnobs)
    .addWithJSX('UserProfileHeader', () => {
        const displayName = text('Display Name', 'Test User');

        return (
            <UserProfileHeader user={{
                id: 'id',
                username: 'username',
                displayName: displayName
            }} />
        );
    }, {
        showFunctions: false
    })
;
