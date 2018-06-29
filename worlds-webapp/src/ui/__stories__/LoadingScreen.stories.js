import React from 'react';
import {storiesOf} from '@storybook/react';
import LoadingScreen from '../LoadingScreen';

storiesOf('LoadingScreen', module)
    .addWithJSX('LoadingScreen', () => (
        <LoadingScreen />
    ), {
        showFunctions: false
    })
;
