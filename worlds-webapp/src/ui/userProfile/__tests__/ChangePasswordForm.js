// @flow

import React from 'react';
import {shallow} from 'enzyme';
import ChangePasswordForm  from '../ChangePasswordForm';

/** Create the component to test */
function setup({passwordMismatch, status, errorCode}: {passwordMismatch?: boolean, status?: string, errorCode?: string} = {}) {
    const onUpdateOldPassword = jest.fn();
    const onUpdateNew1Password = jest.fn();
    const onUpdateNew2Password = jest.fn();
    const onSave = jest.fn();

    return {
        onUpdateOldPassword,
        onUpdateNew1Password,
        onUpdateNew2Password,
        onSave,
        element: shallow(<ChangePasswordForm passwordMismatch={passwordMismatch || false}
                                             onUpdateOldPassword={onUpdateOldPassword}
                                             onUpdateNew1Password={onUpdateNew1Password}
                                             onUpdateNew2Password={onUpdateNew2Password}
                                             onSave={onSave}
                                             status={status}
                                             errorCode={errorCode} />)
    };
}

describe('ChangePasswordForm', () => {
    describe('Renders correctly', () => {
        it('When viewing a blank form', () => {
            const { element } = setup();

            expect(element).toMatchSnapshot();
        });
        it('When passwords are mismatched', () => {
            const { element } = setup({passwordMismatch: true});

            expect(element).toMatchSnapshot();
        });
        it('When successfully saved a profile', () => {
            const { element } = setup({status: 'success'});

            expect(element).toMatchSnapshot();
        });
        it('When failed to save a profile', () => {
            const { element } = setup({status: 'error', errorCode: 'oops'});

            expect(element).toMatchSnapshot();
        });
    });

    it('Triggers onSave when the save button is clicked', () => {
        const { element, onSave, onUpdateOldPassword, onUpdateNew1Password, onUpdateNew2Password } = setup();

        element.find("Button").simulate("click");

        expect(onSave).toHaveBeenCalledTimes(1);

        expect(onUpdateOldPassword).toHaveBeenCalledTimes(0);
        expect(onUpdateNew1Password).toHaveBeenCalledTimes(0);
        expect(onUpdateNew2Password).toHaveBeenCalledTimes(0);
    });

    it('Triggers onUpdateOldPassword when the old password is changed', () => {
        const { element, onSave, onUpdateOldPassword, onUpdateNew1Password, onUpdateNew2Password } = setup();

        element.find("input[name='original']").simulate('change', {
            target: {
                value: 'password'
            }
        });

        expect(onUpdateOldPassword).toHaveBeenCalledTimes(1);
        expect(onUpdateOldPassword).toHaveBeenCalledWith('password');

        expect(onUpdateNew1Password).toHaveBeenCalledTimes(0);
        expect(onUpdateNew2Password).toHaveBeenCalledTimes(0);
        expect(onSave).toHaveBeenCalledTimes(0);
    });

    it('Triggers onUpdateNew1Password when the new password is changed', () => {
        const { element, onSave, onUpdateOldPassword, onUpdateNew1Password, onUpdateNew2Password } = setup();

        element.find("input[name='new1']").simulate('change', {
            target: {
                value: 'newPassword'
            }
        });

        expect(onUpdateNew1Password).toHaveBeenCalledTimes(1);
        expect(onUpdateNew1Password).toHaveBeenCalledWith('newPassword');

        expect(onUpdateOldPassword).toHaveBeenCalledTimes(0);
        expect(onUpdateNew2Password).toHaveBeenCalledTimes(0);
        expect(onSave).toHaveBeenCalledTimes(0);
    });

    it('Triggers onUpdateNew2Password when the repeat password is changed', () => {
        const { element, onSave, onUpdateOldPassword, onUpdateNew1Password, onUpdateNew2Password } = setup();

        element.find("input[name='new2']").simulate('change', {
            target: {
                value: 'newPassword'
            }
        });

        expect(onUpdateNew2Password).toHaveBeenCalledTimes(1);
        expect(onUpdateNew2Password).toHaveBeenCalledWith('newPassword');

        expect(onUpdateOldPassword).toHaveBeenCalledTimes(0);
        expect(onUpdateNew1Password).toHaveBeenCalledTimes(0);
        expect(onSave).toHaveBeenCalledTimes(0);
    });
});
