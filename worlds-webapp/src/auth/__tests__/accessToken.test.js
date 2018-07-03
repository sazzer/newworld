// @flow

import * as testSubject from '../accessToken';

describe('storeAccessTokenAction', () => {
    describe('Generating the action object', () => {
        const result = testSubject.storeAccessTokenAction('accessToken', 'Bearer', 3600);

        it('Has 2 keys', () => {
            expect(Object.keys(result)).toHaveLength(2);
        });
        it('Has the right type', () => {
            expect(result.type).toEqual('AUTH/STORE_ACCESS_TOKEN');
        });
        it('Has an access token', () => {
            expect(result.payload).toEqual({
                accessToken: {
                    token: 'accessToken',
                    type: 'Bearer',
                    expires: 3600
                }
            });
        });
    });
});

describe('clearAccessTokenAction', () => {
    describe('Generating the action object', () => {
        const result = testSubject.clearAccessTokenAction();

        it('Has 1 keys', () => {
            expect(Object.keys(result)).toHaveLength(1);
        });
        it('Has the right type', () => {
            expect(result.type).toEqual('AUTH/CLEAR_ACCESS_TOKEN');
        });
    });
});

describe('storeAccessTokenMutation', () => {
    describe('Specifying new values', () => {
        const state = {};
        testSubject.storeAccessTokenMutation(state, {
            type: '',
            payload: {
                accessToken: {
                    token: 'accessToken',
                    type: 'Bearer',
                    expires: 3600
                }
            }
        });

        it('Has 1 key', () => {
            expect(Object.keys(state)).toHaveLength(1);
        });
        it('Has the right access token', () => {
            expect(state.accessToken).toEqual({
                token: 'accessToken',
                type: 'Bearer',
                expires: 3600
            });
        });
    });
});

describe('clearAccessTokenMutation', () => {
    describe('Clearing existing values', () => {
        const state = {
            accessToken: {
                token: 'accessToken',
                type: 'Bearer',
                expires: 3600
            }
        };
        testSubject.clearAccessTokenMutation(state, {type: ''});

        it('Has 0 keys', () => {
            expect(Object.keys(state)).toHaveLength(0);
        });
    });
});

describe('selectHasAccessToken', () => {
    describe('Access Token is not present', () => {
        const state = { };

        it('Returns false', () => {
            expect(testSubject.selectHasAccessToken(state)).toBe(false);
        })
    });
    describe('Access Token is present', () => {
        const state = {
            accessToken: {
                token: 'accessToken',
                type: 'Bearer',
                expires: 3600
            }
        };

        it('Returns true', () => {
            expect(testSubject.selectHasAccessToken(state)).toBe(true);
        })
    });
});
