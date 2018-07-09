// @flow

import React from 'react';
import {Form, Button} from 'semantic-ui-react'
import {Interpolate} from 'react-i18next';
import type {User} from "../../users/users";
import UserProfileFormSuccessMessage from './UserProfileFormSuccessMessage';
import UserProfileFormErrorMessage from './UserProfileFormErrorMessage';

/** The flow type representing the props for the User Profile Form */
type UserProfileFormProps = {
    user: User,
    onUpdateUsername: (string) => void,
    onUpdateEmail: (string) => void,
    onUpdateDisplayName: (string) => void,
    onSave: () => void,
    status?: string,
    errorCode?: string
};

/**
 * Helper to process the onChange event from a control and call a handler with the new value
 */
function processChange(handler) {
    return function(e) {
        handler(e.target.value);
    }
}

/**
 * The form to display for the user profile
 */
export default function UserProfileForm(props: UserProfileFormProps) {
    let message;

    if (props.status === 'success') {
        message = <UserProfileFormSuccessMessage />;
    } else if (props.status === 'error') {
        message = <UserProfileFormErrorMessage errorCode={props.errorCode} />;
    }

    return (
        <Form success={props.status === 'success'} error={props.status === 'error'} loading={props.status === 'saving'} data-test="userProfileForm">
            <Form.Field>
                <label><Interpolate i18nKey="userProfile.form.email" /></label>
                <input value={props.user.email}
                       name="email"
                       onChange={processChange(props.onUpdateEmail)}
                       required />
            </Form.Field>
            <Form.Field>
                <label><Interpolate i18nKey="userProfile.form.username" /></label>
                <input value={props.user.username}
                       name="username"
                       onChange={processChange(props.onUpdateUsername)}
                       required />
            </Form.Field>
            <Form.Field>
                <label><Interpolate i18nKey="userProfile.form.displayName" /></label>
                <input value={props.user.displayName}
                       name="displayName"
                       onChange={processChange(props.onUpdateDisplayName)}
                       required />
            </Form.Field>
            { message }
            <Button primary onClick={props.onSave}><Interpolate i18nKey="userProfile.form.save" /></Button>
        </Form>
    );
}
