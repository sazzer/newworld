// @flow

import React from 'react';
import {Message} from 'semantic-ui-react';
import {Interpolate} from 'react-i18next';

/**
 * The message to display for the user profile save result
 */
export default function ChangePasswordFormSuccessMessage() {
    return (
        <Message success>
            <Interpolate i18nKey='userProfile.password.messages.success' />
        </Message>
    )
}
