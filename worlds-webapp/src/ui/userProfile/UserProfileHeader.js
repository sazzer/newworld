// @flow

import React from 'react';
import {Header, Icon} from 'semantic-ui-react'
import type {User} from "../../users/users";

/** The flow type representing the props for the User Header */
type UserProfileHeaderProps = {
    user: User
};

/**
 * The header to display for the user profile
 */
export default function UserProfileHeader(props: UserProfileHeaderProps) {
    return (
        <Header as='h2' dividing>
            <Icon name='user' />
            <Header.Content>
                { props.user.displayName }
            </Header.Content>
        </Header>
    );
}
