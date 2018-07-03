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
            expect(result.payload.currentUser).toEqual('someId');
        });
    });
});

describe('clearCurrentUserAction', () => {
    describe('Generating the action object', () => {
        const result = testSubject.clearCurrentUserAction();

        it('Has 1 keys', () => {
            expect(Object.keys(result)).toHaveLength(1);
        });
        it('Has the right type', () => {
            expect(result.type).toEqual('USERS/CLEAR_CURRENT_USER');
        });
    });
});

describe('storeCurrentUserMutation', () => {
    describe('Specifying new values', () => {
        const state = {};
        testSubject.storeCurrentUserMutation(state, {
            type: '',
            payload: {
                currentUser: 'someId'
            }
        });

        it('Has 1 key', () => {
            expect(Object.keys(state)).toHaveLength(1);
        });
        it('Has the right current user ID', () => {
            expect(state.currentUser).toBe('someId');
        });
    });
});

describe('clearCurrentUserMutation', () => {
    describe('Clearing existing values', () => {
        const state = {
            currentUser: 'someId'
        };
        testSubject.clearCurrentUserMutation(state, {type: ''});

        it('Has 0 keys', () => {
            expect(Object.keys(state)).toHaveLength(0);
        });
    });
});

describe('selectCurrentUserId', () => {
    describe('Current User ID is not known', () => {
        const state = { users: {} };

        it('Returns undefined', () => {
            expect(testSubject.selectCurrentUserId(state)).toBeUndefined();
        })
    });
    describe('Current User ID is known', () => {
        const state = { currentUser: 'abc123', users: {} };

        it('Returns the correct value', () => {
            expect(testSubject.selectCurrentUserId(state)).toEqual('abc123');
        })
    });
});

describe('selectCurrentUser', () => {
    describe('Current User ID is not known', () => {
        const state = { users: {} };

        it('Returns undefined', () => {
            expect(testSubject.selectCurrentUser(state)).toBeUndefined();
        })
    });
    describe('Current User ID is known but the user isn\'t', () => {
        const state = { currentUser: 'abc123', users: {} };

        it('Returns undefined', () => {
            expect(testSubject.selectCurrentUser(state)).toBeUndefined();
        })
    });
    describe('Current User ID is known and present', () => {
        const state = {
            currentUser: 'abc123',
            users: {
                abc123: {
                    id: 'abc123',
                    displayName: 'Test User',
                    username: 'testuser'
                }
            }
        };

        it('Returns the correct value', () => {
            expect(testSubject.selectCurrentUser(state)).toEqual({
                id: 'abc123',
                displayName: 'Test User',
                username: 'testuser'
            });
        })
    });
});

describe('selectHasCurrentUser', () => {
    describe('Current User ID is not known', () => {
        const state = { users: {} };

        it('Returns false', () => {
            expect(testSubject.selectHasCurrentUser(state)).toBe(false);
        })
    });
    describe('Current User ID is known but the user isn\'t', () => {
        const state = { currentUser: 'abc123', users: {} };

        it('Returns false', () => {
            expect(testSubject.selectHasCurrentUser(state)).toBe(false);
        })
    });
    describe('Current User ID is known and present', () => {
        const state = {
            currentUser: 'abc123',
            users: {
                abc123: {
                    id: 'abc123',
                    displayName: 'Test User',
                    username: 'testuser'
                }
            }
        };

        it('Returns true', () => {
            expect(testSubject.selectHasCurrentUser(state)).toBe(true);
        })
    });
});
