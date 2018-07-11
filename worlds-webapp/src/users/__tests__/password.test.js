// @flow

import * as testSubject from '../password';

describe('changePasswordAction', () => {
    describe('Generating the action object', () => {
        const onSuccess = () => {};
        const onError = () => {};

        const result = testSubject.changePasswordAction('userId', 'old', 'new', onSuccess, onError);

        it('Has 3 keys', () => {
            expect(Object.keys(result)).toHaveLength(3);
        });
        it('Has the right type', () => {
            expect(result.type).toEqual('USERS/CHANGE_PASSWORD');
        });
        it('Has a user ID', () => {
            expect(result.payload.userId).toEqual('userId');
        });
        it('Has a old password', () => {
            expect(result.payload.oldPassword).toEqual('old');
        });
        it('Has a new password', () => {
            expect(result.payload.newPassword).toEqual('new');
        });
        it('Has the correct success callback', () => {
            expect(result.meta.onSuccess).toBe(onSuccess);
        });
        it('Has the correct error callback', () => {
            expect(result.meta.onError).toBe(onError);
        });
    });
});
