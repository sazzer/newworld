import React from 'react';
import {storiesOf} from '@storybook/react';
import ChangePasswordFormSuccessMessage from '../ChangePasswordFormSuccessMessage';

storiesOf('UserProfile/ChangePassword/SuccessMessage', module)
    .addWithJSX('ChangePasswordFormSuccessMessage', () => {

        return (
            <ChangePasswordFormSuccessMessage />
        );
    }, {
        showFunctions: false
    })
;
