import React from 'react';
import {storiesOf} from '@storybook/react';
import {text, withKnobs} from '@storybook/addon-knobs/react';
import {action} from '@storybook/addon-actions';
import UserProfileForm from '../UserProfileForm';

storiesOf('UserProfile/UserProfile/Form', module)
    .addDecorator(withKnobs)
    .addWithJSX('View Profile', () => {
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
    .addWithJSX('Saving Profile', () => {
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
                             status='saving'
            />
        );
    }, {
        showFunctions: false
    })
    .addWithJSX('Saved Profile Successfully', () => {
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
                             status='success'
            />
        );
    }, {
        showFunctions: false
    })
    .addWithJSX('Failed to save profile', () => {
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
                             status='error'
                             errorCode='tag:grahamcox.co.uk,2018,users/problems/duplicate-username'
            />
        );
    }, {
        showFunctions: false
    })
;
