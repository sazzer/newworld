import React from 'react';
import {storiesOf} from '@storybook/react';
import {MissingAccessToken} from '../HasAccessToken';

storiesOf('MissingAccessToken', module)
    .addWithJSX('MissingAccessToken', () => (
        <MissingAccessToken />
    ), {
        showFunctions: false
    })
;
