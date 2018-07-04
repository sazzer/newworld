import React from 'react';
import {storiesOf} from '@storybook/react';
import UserProfileFormErrorMessage from '../UserProfileFormErrorMessage';

storiesOf('UserProfileFormErrorMessage', module)
    .addWithJSX('No error code', () => {

        return (
            <UserProfileFormErrorMessage />
        );
    })
    .addWithJSX('Unrecognized error code', () => {

        return (
            <UserProfileFormErrorMessage errorCode='unrecognized' />
        );
    })
    .addWithJSX('Duplicate Username', () => {

        return (
            <UserProfileFormErrorMessage errorCode='tag:grahamcox.co.uk,2018,users/problems/duplicate-username' />
        );
    })
;
