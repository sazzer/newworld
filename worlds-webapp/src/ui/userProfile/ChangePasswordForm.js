// @flow

import React from 'react';
import {Button, Form} from 'semantic-ui-react'
import {Interpolate} from 'react-i18next';
import ChangePasswordFormErrorMessage from './ChangePasswordFormErrorMessage';

/** The flow type representing the props for the Change Password Form */
type ChangePasswordFormProps = {
    passwordMismatch: boolean,
    onUpdateOldPassword: (string) => void,
    onUpdateNew1Password: (string) => void,
    onUpdateNew2Password: (string) => void,
    onSave: () => void,
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
 * The form to change the users password
 */
export default function ChangePasswordForm(props: ChangePasswordFormProps) {
    let message, error;

    if (props.passwordMismatch) {
        message = <ChangePasswordFormErrorMessage errorCode='passwordMismatch' />;
        error = true;
    }

    return (
        <Form data-test="changePasswordForm" error={error}>
            <Form.Field required>
                <label><Interpolate i18nKey="userProfile.password.original" /></label>
                <input name="original"
                       type="password"
                       onChange={processChange(props.onUpdateOldPassword)}
                       required
                       autoFocus/>
            </Form.Field>
            <Form.Field required>
                <label><Interpolate i18nKey="userProfile.password.new1" /></label>
                <input name="new1"
                       type="password"
                       onChange={processChange(props.onUpdateNew1Password)}
                       required />
            </Form.Field>
            <Form.Field required>
                <label><Interpolate i18nKey="userProfile.password.new2" /></label>
                <input name="new2"
                       type="password"
                       onChange={processChange(props.onUpdateNew2Password)}
                       required />
            </Form.Field>
            { message }
            <Button primary
                    disabled={props.passwordMismatch}
                    onClick={props.onSave}><Interpolate i18nKey="userProfile.password.save" /></Button>
        </Form>
    );
}
