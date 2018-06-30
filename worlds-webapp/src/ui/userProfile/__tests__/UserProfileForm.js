// @flow

import React from 'react';
import {shallow} from 'enzyme';
import UserProfileForm  from '../UserProfileForm';

/** Create the component to test */
function setup() {
    const user = {
        id: 'id',
        displayName: 'Test User',
        username: 'testuser'
    };

    const onUpdateEmail = jest.fn();
    const onUpdateUsername = jest.fn();
    const onUpdateDisplayName = jest.fn();
    const onSave = jest.fn();

    return {
        user,
        onUpdateEmail,
        onUpdateUsername,
        onUpdateDisplayName,
        onSave,
        element: shallow(<UserProfileForm user={user}
                                          onUpdateEmail={onUpdateEmail}
                                          onUpdateUsername={onUpdateUsername}
                                          onUpdateDisplayName={onUpdateDisplayName}
                                          onSave={onSave} />)
    };
}

describe('UserProfileForm', () => {
    it('Renders correctly', () => {
        const { element } = setup();

        expect(element).toMatchSnapshot();
    });

    it('Triggers onSave when the save button is clicked', () => {
        const { element, onSave, onUpdateEmail, onUpdateUsername, onUpdateDisplayName } = setup();

        element.find("Button").simulate("click");

        expect(onSave).toHaveBeenCalledTimes(1);

        expect(onUpdateEmail).toHaveBeenCalledTimes(0);
        expect(onUpdateUsername).toHaveBeenCalledTimes(0);
        expect(onUpdateDisplayName).toHaveBeenCalledTimes(0);
    });

    it('Triggers onUpdateEmail when the email address is changed', () => {
        const { element, onSave, onUpdateEmail, onUpdateUsername, onUpdateDisplayName } = setup();

        element.find("input[name='email']").simulate('change', {
            target: {
                value: 'new@example.com'
            }
        });

        expect(onUpdateEmail).toHaveBeenCalledTimes(1);
        expect(onUpdateEmail).toHaveBeenCalledWith('new@example.com');

        expect(onUpdateUsername).toHaveBeenCalledTimes(0);
        expect(onUpdateDisplayName).toHaveBeenCalledTimes(0);
        expect(onSave).toHaveBeenCalledTimes(0);
    });

    it('Triggers onUpdateUsername when the username is changed', () => {
        const { element, onSave, onUpdateEmail, onUpdateUsername, onUpdateDisplayName } = setup();

        element.find("input[name='username']").simulate('change', {
            target: {
                value: 'newUser'
            }
        });

        expect(onUpdateUsername).toHaveBeenCalledTimes(1);
        expect(onUpdateUsername).toHaveBeenCalledWith('newUser');

        expect(onUpdateEmail).toHaveBeenCalledTimes(0);
        expect(onUpdateDisplayName).toHaveBeenCalledTimes(0);
        expect(onSave).toHaveBeenCalledTimes(0);
    });

    it('Triggers onUpdateDisplayName when the display name is changed', () => {
        const { element, onSave, onUpdateEmail, onUpdateUsername, onUpdateDisplayName } = setup();

        element.find("input[name='displayName']").simulate('change', {
            target: {
                value: 'New User'
            }
        });

        expect(onUpdateDisplayName).toHaveBeenCalledTimes(1);
        expect(onUpdateDisplayName).toHaveBeenCalledWith('New User');

        expect(onUpdateEmail).toHaveBeenCalledTimes(0);
        expect(onUpdateUsername).toHaveBeenCalledTimes(0);
        expect(onSave).toHaveBeenCalledTimes(0);
    });
});
