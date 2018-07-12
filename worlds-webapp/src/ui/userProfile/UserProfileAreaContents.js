// @flow

import React from 'react';
import {Grid, Menu} from 'semantic-ui-react';
import {Interpolate} from 'react-i18next';
import UserProfileForm from './UserProfileFormWrapper';
import ChangePasswordForm from './ChangePasswordFormWrapper';
import type {User} from "../../users/users";

/** The tag for the profile pane */
const PROFILE = 'profile';

/** The tag for the password pane */
const PASSWORD = 'password';

/** The flow type representing the props for the User Profile Contents */
type UserProfileAreaContentsProps = {
    user: User,
    active: string,
    onChangeActive: (string) => void,
    onSaveProfile: (User, () => void, (string) => void) => void,
    onSavePassword: (string, string, string, () => void, (string) => void) => void
};

/**
 * The actual contents area of the User Profile screen
 */
export default function UserProfileAreaContents(props: UserProfileAreaContentsProps) {
    let contents;

    if (props.active === PROFILE) {
        contents = <UserProfileForm user={props.user} onSave={props.onSaveProfile} key={PROFILE} />;
    } else if (props.active === PASSWORD) {
        contents = <ChangePasswordForm user={props.user} onSave={props.onSavePassword} key={PASSWORD} />;
    }

    return (
        <Grid stackable columns={2}>
            <Grid.Column width={4}>
                <Menu pointing vertical>
                    <Menu.Item active={props.active === PROFILE} onClick={() => props.onChangeActive(PROFILE)} data-test="userProfileMenuProfile">
                        <Interpolate i18nKey="userProfile.menu.profile" />
                    </Menu.Item>
                    <Menu.Item active={props.active === PASSWORD} onClick={() => props.onChangeActive(PASSWORD)} data-test="userProfileMenuPassword">
                        <Interpolate i18nKey="userProfile.menu.password" />
                    </Menu.Item>
                </Menu>
            </Grid.Column>
            <Grid.Column stretched width={12}>
                { contents }
            </Grid.Column>
        </Grid>
    );
}
