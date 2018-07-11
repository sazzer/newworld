// @flow

import React from 'react';
import {Message} from 'semantic-ui-react';
import {Interpolate} from 'react-i18next';

/** The message keys for the errors we can support */
const problemKeys = {
    'tag:grahamcox.co.uk,2018,users/problems/invalid-password': 'userProfile.password.messages.error.invalidPassword',
    'passwordMismatch': 'userProfile.password.messages.error.passwordMismatch',
    'undefined': 'userProfile.password.messages.error.unexpected'
};

/** The flow type representing the props for the Change Password Form message */
type ChangePasswordFormErrorMessageProps = {
    errorCode?: string
};

/**
 * The message to display for the change password save result
 */
export default function ChangePasswordFormErrorMessage(props: ChangePasswordFormErrorMessageProps) {
    let problemCode = props.errorCode || 'undefined';

    if (!problemKeys.hasOwnProperty(problemCode)) {
        problemCode = 'undefined';
    }
    const errorKey = problemKeys[problemCode];

    return (
        <Message error>
            <Interpolate i18nKey={errorKey} />
        </Message>
    )
}
