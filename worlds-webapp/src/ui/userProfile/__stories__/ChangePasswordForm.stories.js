import React from 'react';
import {storiesOf} from '@storybook/react';
import {action} from '@storybook/addon-actions';
import ChangePasswordForm from '../ChangePasswordForm';

storiesOf('ChangePasswordForm', module)
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
;
