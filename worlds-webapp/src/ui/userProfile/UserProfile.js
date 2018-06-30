// @flow

import React from 'react';
import {connectStore} from "redux-box";
import type {UserModule} from "../../users";
import type {User} from "../../users/users";
import {module as usersModule} from "../../users";
import UserProfileBreadcrumbs from './UserProfileBreadcrumbs';
import UserProfileHeader from './UserProfileHeader';
import UserProfileForm from './UserProfileFormWrapper';
import LoadingScreen from '../LoadingScreen';

/** The flow type representing the props for the User Breadcrumbs */
type UserProfileProps = {
    user: ?User,
    onSaveProfile: (User) => void
};

/**
 * The actual user profile screen
 * @return {*}
 * @constructor
 */
export function UserProfile(props: UserProfileProps) {
    if (props.user) {
        return (
            <div>
                <UserProfileBreadcrumbs user={props.user} />
                <UserProfileHeader user={props.user} />
                <UserProfileForm user={props.user} onSave={props.onSaveProfile}/>
            </div>
        );
    } else {
        return <LoadingScreen />;
    }
}

/**
 * Wrapper around the User Profile that connects it to Redux
 */
export function ConnectedUserProfile({users}: {users: UserModule}) {
    return (
        <UserProfile user={users.selectCurrentUser()} onSaveProfile={users.saveUser} />
    )
}

export default connectStore({users: usersModule})(ConnectedUserProfile);
