import React from 'react';
import {storiesOf} from '@storybook/react';
import UserProfileFormSuccessMessage from '../UserProfileFormSuccessMessage';

storiesOf('UserProfileFormSuccessMessage', module)
    .addWithJSX('UserProfileFormSuccessMessage', () => {

        return (
            <UserProfileFormSuccessMessage />
        );
    }, {
        showFunctions: false
    })
;
