// @flow

import React from 'react';
import type {User} from "../../users/users";
import UserProfileAreaContents from './UserProfileAreaContents';

/** The flow type representing the props for the User Profile Contents */
type UserProfileAreaContentsProps = {
    user: User,
    onSaveProfile: (User, () => void, (string) => void) => void,
    onSavePassword: (string, string, string, () => void, (string) => void) => void
};

/** The flow type representing the state for the User Profile Contents */
type UserProfileAreaContentsState = {
    active: string
};

/**
 * React component that wraps the User Profile Contents to maintain the state
 */
export default class UserProfileAreaContentsWrapper extends React.Component<UserProfileAreaContentsProps, UserProfileAreaContentsState> {
    handleChangeActive = this._onChangeActive.bind(this);

    state: UserProfileAreaContentsState = {
        active: 'profile'
    };

    /**
     * Render the component
     */
    render() {
        return (
            <UserProfileAreaContents user={this.props.user}
                                     onSaveProfile={this.props.onSaveProfile}
                                     onSavePassword={this.props.onSavePassword}
                                     active={this.state.active}
                                     onChangeActive={this.handleChangeActive} />
        )
    }

    /**
     * Change the active pane
     */
    _onChangeActive(active: string) {
        this.setState({
            active
        });
    }
}
