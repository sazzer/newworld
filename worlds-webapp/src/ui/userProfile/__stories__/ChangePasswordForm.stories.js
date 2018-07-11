import React from 'react';
import {storiesOf} from '@storybook/react';
import {action} from '@storybook/addon-actions';
import ChangePasswordForm from '../ChangePasswordForm';

storiesOf('UserProfile/ChangePassword/Form', module)
    .addWithJSX('Blank', () => {
        return (
            <ChangePasswordForm
                onUpdateOldPassword={action('onUpdateOldPassword')}
                onUpdateNew1Password={action('onUpdateNew1Password')}
                onUpdateNew2Password={action('onUpdateNew2Password')}
                onSave={action('onSave')}
            />
        );
    }, {
        showFunctions: false
    })
    .addWithJSX('Password Mismatch', () => {
        return (
            <ChangePasswordForm
                passwordMismatch
                onUpdateOldPassword={action('onUpdateOldPassword')}
                onUpdateNew1Password={action('onUpdateNew1Password')}
                onUpdateNew2Password={action('onUpdateNew2Password')}
                onSave={action('onSave')}
            />
        );
    }, {
        showFunctions: false
    })
    .addWithJSX('Saving', () => {
        return (
            <ChangePasswordForm
                onUpdateOldPassword={action('onUpdateOldPassword')}
                onUpdateNew1Password={action('onUpdateNew1Password')}
                onUpdateNew2Password={action('onUpdateNew2Password')}
                onSave={action('onSave')}
                status="saving"
            />
        );
    }, {
        showFunctions: false
    })
    .addWithJSX('Successfully Saved', () => {
        return (
            <ChangePasswordForm
                onUpdateOldPassword={action('onUpdateOldPassword')}
                onUpdateNew1Password={action('onUpdateNew1Password')}
                onUpdateNew2Password={action('onUpdateNew2Password')}
                onSave={action('onSave')}
                status="success"
            />
        );
    }, {
        showFunctions: false
    })
    .addWithJSX('Error Saving', () => {
        return (
            <ChangePasswordForm
                onUpdateOldPassword={action('onUpdateOldPassword')}
                onUpdateNew1Password={action('onUpdateNew1Password')}
                onUpdateNew2Password={action('onUpdateNew2Password')}
                onSave={action('onSave')}
                status="error"
                errorCode=""
            />
        );
    }, {
        showFunctions: false
    })
;
