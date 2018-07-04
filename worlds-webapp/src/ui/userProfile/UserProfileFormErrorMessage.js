// @flow

import React from 'react';
import {Message} from 'semantic-ui-react';
import {Interpolate} from 'react-i18next';

/** The message keys for the errors we can support */
const problemKeys = {
    'tag:grahamcox.co.uk,2018,users/problems/duplicate-username': 'userProfile.form.messages.error.duplicateUsername',
    'undefined': 'userProfile.form.messages.error.unexpected'
}

/** The flow type representing the props for the User Profile Form message */
type UserProfileFormErrorMessageProps = {
    errorCode?: string
};

/**
 * The message to display for the user profile save result
 */
export default function UserProfileFormErrorMessage(props: UserProfileFormErrorMessageProps) {
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
