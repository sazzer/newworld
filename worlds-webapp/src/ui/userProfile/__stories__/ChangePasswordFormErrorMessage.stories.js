import React from 'react';
import {storiesOf} from '@storybook/react';
import ChangePasswordFormErrorMessage from '../ChangePasswordFormErrorMessage';

storiesOf('ChangePasswordFormErrorMessage', module)
    .addWithJSX('No error code', () => {

        return (
            <ChangePasswordFormErrorMessage />
        );
    })
    .addWithJSX('Unrecognized error code', () => {

        return (
            <ChangePasswordFormErrorMessage errorCode='unrecognized' />
        );
    })
    .addWithJSX('Password Mismatch', () => {

        return (
            <ChangePasswordFormErrorMessage errorCode='passwordMismatch' />
        );
    })
;
