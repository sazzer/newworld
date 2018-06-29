// @flow

import React from 'react';
import {Dimmer, Loader, Segment} from 'semantic-ui-react';
import {Interpolate} from 'react-i18next';

/**
 * Component to show a loading screen
 */
export default function LoadingScreen() {
    return (
        <Segment raised style={{minHeight: "10em"}}>
            <Dimmer active inverted>
                <Loader size='large' inline="centered">
                    <Interpolate i18nKey="loading.message" />
                </Loader>
            </Dimmer>
        </Segment>
    );
}
