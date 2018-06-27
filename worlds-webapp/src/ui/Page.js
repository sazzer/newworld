// @flow

import React from 'react';
import { Container } from 'semantic-ui-react';
import { Switch, Route } from 'react-router-dom';
import UserProfile from './userProfile';

/**
 * The actual main page of the application
 */
export default function Page() {
    return (
        <Container>
            <Switch>
                <Route exact path="/profile" component={UserProfile} />
            </Switch>
        </Container>
    );
}
