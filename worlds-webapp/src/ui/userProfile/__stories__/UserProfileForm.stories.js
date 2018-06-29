import React from 'react';
import {storiesOf} from '@storybook/react';
import {text, withKnobs} from '@storybook/addon-knobs/react';
import { action} from '@storybook/addon-actions';
import UserProfileForm from '../UserProfileForm';

storiesOf('UserProfileForm', module)
    .addDecorator(withKnobs)
    .addWithJSX('UserProfileForm', () => {
        const userId = text('ID', 'userId');
        const username = text('Username', 'testuser');
        const displayName = text('Display Name', 'Test User');
        const email = text('Email Address', 'test@example.com');

        return (
            <UserProfileForm user={{
                id: userId,
                username: username,
                displayName: displayName,
                email: email
            }}
                             onUpdateUsername={action('onUpdateUsername')}
                             onUpdateEmail={action('onUpdateEmail')}
                             onUpdateDisplayName={action('onUpdateDisplayName')}
                             onSave={action('onSave')}
            />
        );
    }, {
        showFunctions: false
    })
;
