import React from 'react';
import {storiesOf} from '@storybook/react';
import ChangePasswordForm from '../ChangePasswordForm';

storiesOf('ChangePasswordForm', module)
    .addWithJSX('ChangePasswordForm', () => {
        return (
            <ChangePasswordForm />
        );
    }, {
        showFunctions: false
    })
;
