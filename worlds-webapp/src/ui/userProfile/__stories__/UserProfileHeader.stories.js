import React from 'react';
import {storiesOf} from '@storybook/react';
import {text, withKnobs} from '@storybook/addon-knobs/react';
import UserProfileHeader from '../UserProfileHeader';

storiesOf('UserProfile/Header', module)
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
