// @flow

import React from 'react';
import HasAccessToken from '../HasAccessToken';
import UserProfile from './UserProfile';

/**
 * Wrapper around the User Profile to only show it if logged in
 */
export default function UserProfileWrapper() {
    return (
        <HasAccessToken>
            <UserProfile/>
        </HasAccessToken>
    );
}
