// @flow

import React from 'react';
import {Form, Button} from 'semantic-ui-react'
import {Interpolate} from 'react-i18next';
import type {User} from "../../users/users";

/** The flow type representing the props for the User Profile Form */
type UserProfileFormProps = {
    user: User,
    onUpdateUsername: (string) => void,
    onUpdateEmail: (string) => void,
    onUpdateDisplayName: (string) => void,
    onSave: () => void
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
    return (
        <Form>
            <Form.Field>
                <label>Email Address</label>
                <input value={props.user.email}
                       name="email"
                       onChange={processChange(props.onUpdateEmail)}
                       required />
            </Form.Field>
            <Form.Field>
                <label>Username</label>
                <input value={props.user.username}
                       name="username"
                       onChange={processChange(props.onUpdateUsername)}
                       required />
            </Form.Field>
            <Form.Field>
                <label>Display Name</label>
                <input value={props.user.displayName}
                       name="displayName"
                       onChange={processChange(props.onUpdateDisplayName)}
                       required />
            </Form.Field>
            <Button primary onClick={props.onSave}>Save</Button>
        </Form>
    );
}
