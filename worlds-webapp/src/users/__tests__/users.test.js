// @flow

import * as testSubject from '../users';

describe('loadUserAction', () => {
    describe('Generating the action object', () => {
        const result = testSubject.loadUserAction('someUser');

        it('Has 2 keys', () => {
            expect(Object.keys(result)).toHaveLength(2);
        });
        it('Has the right type', () => {
            expect(result.type).toEqual('USERS/LOAD_USER');
        });
        it('Has a current user ID', () => {
            expect(result.userId).toEqual('someUser');
        });
    });

});

describe('storeUserMutation', () => {
    describe('Specifying new values', () => {
        const state = {
            users: {}
        };

        testSubject.storeUserMutation(state, {type: '', user: {
            id: 'someId',
            username: 'testUser',
            email: 'email@example.com',
            displayName: 'Test User'
        }});

        it('Has 1 key', () => {
            expect(Object.keys(state)).toHaveLength(1);
        });

        it('Has 1 user', () => {
            expect(Object.keys(state.users)).toHaveLength(1);
        });

        it('Has the right user', () => {
            expect(state.users['someId']).toEqual({
                id: 'someId',
                username: 'testUser',
                email: 'email@example.com',
                displayName: 'Test User'
            });
        });
    });

});
