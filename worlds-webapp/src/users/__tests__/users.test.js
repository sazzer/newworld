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
            expect(result.payload.userId).toEqual('someUser');
        });
    });
});

describe('saveUserAction', () => {
    describe('Generating the action object', () => {
        const result = testSubject.saveUserAction({
            id: 'someId',
            username: 'testUser',
            email: 'email@example.com',
            displayName: 'Test User'
        });

        it('Has 2 keys', () => {
            expect(Object.keys(result)).toHaveLength(2);
        });
        it('Has the right type', () => {
            expect(result.type).toEqual('USERS/SAVE_USER');
        });
        it('Has a current user', () => {
            expect(result.payload.user).toEqual({
                id: 'someId',
                username: 'testUser',
                email: 'email@example.com',
                displayName: 'Test User'
            });
        });
    });
});

describe('storeUserMutation', () => {
    describe('Specifying new values', () => {
        const state = {
            users: {}
        };

        testSubject.storeUserMutation(state, {
            type: '',
            payload: {
                user: {
                    id: 'someId',
                    username: 'testUser',
                    email: 'email@example.com',
                    displayName: 'Test User'
                }
            }
        });

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

describe('selectUsers', () => {
    describe('When no users are known', () => {
        const state = { users: {} };

        it('Returns an empty object', () => {
            expect(testSubject.selectUsers(state)).toEqual({});
        });
    });
    describe('When a user is known', () => {
        const state = {
            users: {
                abc123: {
                    id: 'abc123',
                    displayName: 'Test User',
                    username: 'testuser'
                }
            }
        };

        it('Returns an empty object', () => {
            expect(testSubject.selectUsers(state)).toEqual({
                abc123: {
                    id: 'abc123',
                    displayName: 'Test User',
                    username: 'testuser'
                }
            });
        });
    });
});

describe('selectUserById', () => {
    const state = {
        users: {
            abc123: {
                id: 'abc123',
                displayName: 'Test User',
                username: 'testuser'
            }
        }
    };

    describe('When the user is unknown', () => {
        it('Returns undefined', () => {
            expect(testSubject.selectUserById(state, 'unknown')).toBeUndefined();
        })
    });

    describe('When the user is known', () => {
        it('Returns the user', () => {
            expect(testSubject.selectUserById(state, 'abc123')).toEqual({
                id: 'abc123',
                displayName: 'Test User',
                username: 'testuser'
            });
        })
    });
});
