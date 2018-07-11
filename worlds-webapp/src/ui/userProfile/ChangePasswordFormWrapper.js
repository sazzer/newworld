// @flow

import React from 'react';
import ChangePasswordForm from './ChangePasswordForm';

/** The flow type representing the props for the Change Password Form */
type ChangePasswordFormProps = {
    onSave: (string, string, () => void, (string) => void) => void
};

/** The flow type representing the state for the Change Password Form */
type ChangePasswordFormState = {
    oldPassword: string,
    new1Password: string,
    new2Password: string,
    status?: string,
    errorCode?: string
};

/**
 * React component that wraps the User Profile Form to maintain the state
 */
export default class ChangePasswordFormWrapper extends React.Component<ChangePasswordFormProps, ChangePasswordFormState> {

    handleUpdateOldPassword = this._onUpdateOldPassword.bind(this);
    handleUpdateNew1Password = this._onUpdateNew1Password.bind(this);
    handleUpdateNew2Password = this._onUpdateNew2Password.bind(this);
    handleSave = this._onSave.bind(this);

    state: ChangePasswordFormState = {
        oldPassword: '',
        new1Password: '',
        new2Password: ''
    };

    /**
     * Render the form
     */
    render() {
        const {new1Password, new2Password} = this.state;
        const passwordMismatch = new1Password.length > 0 && new2Password.length > 0 && new1Password !== new2Password;

        return (
            <ChangePasswordForm passwordMismatch={passwordMismatch}
                                onUpdateOldPassword={this.handleUpdateOldPassword}
                                onUpdateNew1Password={this.handleUpdateNew1Password}
                                onUpdateNew2Password={this.handleUpdateNew2Password}
                                onSave={this.handleSave}
                                status={this.state.status}
                                errorCode={this.state.errorCode} />
        )
    }

    /**
     * Handle any updates to the email address
     * @param newVal the new value from the form
     * @private
     */
    _onUpdateOldPassword(newVal: string) {
        this.setState({
            oldPassword: newVal
        });
    }

    /**
     * Handle any updates to the username
     * @param newVal the new value from the form
     * @private
     */
    _onUpdateNew1Password(newVal: string) {
        this.setState({
            new1Password: newVal
        });
    }

    /**
     * Handle any updates to the display name
     * @param newVal the new value from the form
     * @private
     */
    _onUpdateNew2Password(newVal: string) {
        this.setState({
            new2Password: newVal
        });
    }

    /**
     * Attempt to save the changes
     * @private
     */
    _onSave() {
        const {oldPassword, new1Password, new2Password} = this.state;
        if (oldPassword.length > 0 && new1Password.length > 0 && new2Password.length && new1Password === new2Password) {
            this.setState({
                status: 'saving',
                errorCode: undefined
            });

            this.props.onSave(oldPassword, new1Password, () => {
                this.setState({
                    status: 'success',
                    errorCode: undefined
                });
            }, (error) => {
                this.setState({
                    status: 'error',
                    errorCode: error
                });
            })
        }
    }
}
