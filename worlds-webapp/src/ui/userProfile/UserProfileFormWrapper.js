// @flow

import React from 'react';
import type {User} from "../../users/users";
import UserProfileForm from './UserProfileForm';

/** The flow type representing the props for the User Profile Form */
type UserProfileFormProps = {
    user: User,
    onSave: (User, () => void, (string) => void) => void
};

/** The flow type representing the state for the User Profile Form */
type UserProfileFormState = {
    user: User,
    status?: string,
    errorCode?: string
};

/**
 * React component that wraps the User Profile Form to maintain the state
 */
export default class UserProfileFormWrapper extends React.Component<UserProfileFormProps, UserProfileFormState> {

    handleUpdateEmail = this._onUpdateEmail.bind(this);
    handleUpdateUsername = this._onUpdateUsername.bind(this);
    handleUpdateDisplayName = this._onUpdateDisplayName.bind(this);
    handleSave = this._onSave.bind(this);

    state: UserProfileFormState = {
        user: {...this.props.user}
    };

    /**
     * Render the form
     */
    render() {
        return (
            <UserProfileForm user={this.state.user}
                             onUpdateEmail={this.handleUpdateEmail}
                             onUpdateUsername={this.handleUpdateUsername}
                             onUpdateDisplayName={this.handleUpdateDisplayName}
                             onSave={this.handleSave}
                             status={this.state.status}
                             errorCode={this.state.errorCode}/>
        )
    }

    /**
     * Handle any updates to the email address
     * @param newVal the new value from the form
     * @private
     */
    _onUpdateEmail(newVal: string) {
        const { user } = this.state;
        user.email = newVal;
        this.setState({user});
    }

    /**
     * Handle any updates to the username
     * @param newVal the new value from the form
     * @private
     */
    _onUpdateUsername(newVal: string) {
        const { user } = this.state;
        user.username = newVal;
        this.setState({user});
    }

    /**
     * Handle any updates to the display name
     * @param newVal the new value from the form
     * @private
     */
    _onUpdateDisplayName(newVal: string) {
        const { user } = this.state;
        user.displayName = newVal;
        this.setState({user});
    }

    /**
     * Attempt to save the changes
     * @private
     */
    _onSave() {
        const { user } = this.state;

        this.setState({
            status: 'saving',
            errorCode: undefined
        });

        this.props.onSave(user, () => {
            this.setState({
                status: 'success',
                errorCode: undefined
            });
        }, (error) => {
            this.setState({
                status: 'error',
                errorCode: error
            });
        });
    }
}
