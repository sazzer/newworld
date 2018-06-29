import React from 'react';
import {storiesOf} from '@storybook/react';
import { withKnobs, text } from '@storybook/addon-knobs/react';
import { UserProfile } from '../UserProfile';

storiesOf('UserProfile', module)
    .addDecorator(withKnobs)
    .addWithJSX('User Present', () => {
        const userId = text('ID', 'userId');
        const username = text('Username', 'testuser');
        const displayName = text('Display Name', 'Test User');
        const email = text('Email Address', 'test@example.com');

        return (
            <UserProfile user={{
                id: userId,
                username: username,
                displayName: displayName,
                email: email
            }} />
        );
    }, {
        showFunctions: false
    })
    .addWithJSX('User Absent', () => {
        return (
            <UserProfile />
        );
    }, {
        showFunctions: false
    })
;
