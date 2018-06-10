// @flow

import * as testSubject from '../currentUser';

describe('storeCurrentUserAction', () => {
    describe('Generating the action object', () => {
        const result = testSubject.storeCurrentUserAction('someId');

        it('Has 2 keys', () => {
            expect(Object.keys(result)).toHaveLength(2);
        });
        it('Has the right type', () => {
            expect(result.type).toEqual('USERS/STORE_CURRENT_USER');
        });
        it('Has a current user ID', () => {
            expect(result.current_user).toEqual('someId');
        });
    });
});

describe('storeCurrentUserMutation', () => {
    describe('Specifying new values', () => {
        const state = {};
        testSubject.storeCurrentUserMutation(state, {type: '', current_user: 'someId'});

        it('Has 1 key', () => {
           expect(Object.keys(state)).toHaveLength(1);
        });
        it('Has the right current user ID', () => {
            expect(state.current_user).toBe('someId');
        });
    });
});
