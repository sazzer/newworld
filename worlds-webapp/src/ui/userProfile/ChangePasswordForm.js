// @flow

import React from 'react';
import {Button, Form} from 'semantic-ui-react'
import {Interpolate} from 'react-i18next';

/** The flow type representing the props for the Change Password Form */
type ChangePasswordFormProps = {
};

/**
 * The form to change the users password
 */
export default function ChangePasswordForm(props: ChangePasswordFormProps) {
    return (
        <Form data-test="changePasswordForm">
            <Form.Field>
                <label><Interpolate i18nKey="userProfile.password.original" /></label>
                <input name="original"
                       type="password"
                       required
                       autoFocus/>
            </Form.Field>
            <Form.Field>
                <label><Interpolate i18nKey="userProfile.password.new1" /></label>
                <input name="new1"
                       type="password"
                       required />
            </Form.Field>
            <Form.Field>
                <label><Interpolate i18nKey="userProfile.password.new2" /></label>
                <input name="new2"
                       type="password"
                       required />
            </Form.Field>
            <Button primary><Interpolate i18nKey="userProfile.password.save" /></Button>
        </Form>
    );
}
