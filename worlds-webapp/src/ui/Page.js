// @flow

import React from 'react';
import { Container, Grid } from 'semantic-ui-react';
import { Switch, Route } from 'react-router-dom';
import UserProfile from './userProfile';

/**
 * The actual main page of the application
 */
export default function Page() {
    return (
        <Container>
            <Grid padded="vertically" columns={1}>
                <Grid.Column>
                    <Switch>
                        <Route exact path="/profile" component={UserProfile} />
                    </Switch>
                </Grid.Column>
            </Grid>
        </Container>
    );
}
