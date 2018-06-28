// @flow

import React from 'react';
import HasAccessToken from '../HasAccessToken';

export default function UserProfile() {
    return (
        <HasAccessToken>
            <div>
                User Profile Here
            </div>
        </HasAccessToken>
    );
}
